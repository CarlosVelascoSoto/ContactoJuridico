$("#camera-modal").css(
	"background-image",
	"url(" + ctx + "/resources/assets/images/woodpattern.jpg)"
);
/*const camera = document.getElementById('camera');
const preview1 = document.getElementById('preview1');
const cameraRoll = $('#cameraRoll');
let logo = null;*/

//Para Obtener informacion del input e imprimirla en un img
let imagenArrastrada = null;
const inputElement = document.getElementById("files");
const imageElement = document.getElementById("sectionImg");
inputElement.addEventListener("input", load);

var sortable = Sortable.create(imageElement);
/*


function addImageToView(file){
	var photo = $('<div onclick="showpreview(this);"><div/>');
	photo.css({
		'background-image': 'url("' + URL.createObjectURL(file) + '")'
	});
	photo.addClass('cameraRoll');
		cameraRoll.append(photo);
	};

function showpreview(e){
	var $style=$(e).attr('style');
	var $url=$style.replace(/.*background-image:\s?url\(["']blob(.*)["']\).* /,'blob$1');
	$('#preview1').attr('src',$url);
	$('.boxpreview').fadeToggle();
	$('#camdesktop').css('z-index',999);
};
*/
const names=()=>{
	let docsTitles=[]
	let dropZ=document.querySelector(".dropzone")
	let docs=dropZ.querySelectorAll(".link")
	docs.forEach(element=>{
		docsTitles.push(element.getAttribute("title"))
	})
	return docsTitles
}

const verifyNames=()=>{
	var filename = $("#filePDFName").val(),
	docs=names()
	if (!/(.)+(\.pdf)$/i.test(filename)){	filename = filename + ".pdf";}
	
	if(docs.includes(filename)){
		let doc=document.getElementById(filename.replace(/\s/g, ''));
		let choice=window.confirm(`Ya existe un archivo con el nombre ${filename} ¿desea sobreescribir?`)
		if(choice){
			console.log(doc)
			var eventoDobleClic = new Event("dblclick");
			doc.dispatchEvent(eventoDobleClic);
			startupload()
		}
	}else{startupload()}
}

function startupload(targetDZ) {
	var targetDZ = $("#targetDZ").val(),
		filename = $("#filePDFName").val(),
		format = $("#papersize").val(),
		orientation = $("#paperorientation").val(),
		img = setImagenData(),
		f1 = /^[^\\/:\*\?"<>\|]+$/, // f1=Caracteres no válidos \ | / : * ? " < >
		f2 = /^\./; // f2=No inicie con punto (.);;
	if (targetDZ == "") {
		swal(i18n("msg_error"), i18n("err_opencambrowser"), "error");
		return;
	}
	if (!/(.)+(\.pdf)$/i.test(filename))
		// Verifica/inserta la existencia de la extensión ".pdf"	
		filename = filename + ".pdf";
		let docs=names()
	
	/*	var validName = f1.test(filename)&&!f2.test(filename);
	  if(!validName){*/
	if (!(f1.test(filename) && !f2.test(filename))) {
		swal(i18n("msg_error"), i18n("err_enter_filename"), "error");
		return;
	}
	var myDropzone = Dropzone.forElement(targetDZ);

	(imgFrontpage = ctx + "/resources/assets/images/pdf.png"),
		(imgz = setImagenData()),
		(format = $("#papersize").val() || "letter"),
		(unit = "cm");
	if (format == "customize") {
		unit = $("#unit").val();
		format = "[" + $("#sheetwide").val() + ", " + $("#sheethigh").val() + "]";
	}
	window.jsPDF = window.jspdf.jsPDF;
	var doc = new jsPDF({
		orientation: orientation,
		unit: unit,
		format: format,
	});
	imgz.forEach((imageData) => {
		doc.addImage(
			imageData,
			"JPEG",
			0,
			0,
			doc.internal.pageSize.width,
			doc.internal.pageSize.height
		);
		doc.addPage();
	});
	// Elimina la última página
	doc.deletePage(doc.getNumberOfPages());
	doc.name = filename;
	//doc.addImage(imgFrontpage, 'PNG', 10, 10, 50, 50);
	if (document.getElementById(filename.slice(0, -4))) {
		if (
			window.confirm(
				`Ya existe un archivo con el nombre ${filename.slice(
					0,
					-4
				)} desea sobreescribir?`
			)
		) {
			var eventoPersonalizado = new Event("dblclick");
			document
				.getElementById(filename.slice(0, -4))
				.dispatchEvent(eventoPersonalizado);
			var file = new File([doc.output("blob")], filename, {
				type: "application/pdf",
			});
			myDropzone.addFile(file);
			$("#uploadpdf-modal,#camera-modal").modal("hide");
		} else {
		}
	} else {
		var file = new File([doc.output("blob")], filename, {
			type: "application/pdf",
		});
		myDropzone.addFile(file);
		$("#uploadpdf-modal,#camera-modal").modal("hide");
	}
}

//Modal de selección de origen de los archivos. Verifica este modal para activarlo o cerrarlo.
function openModal() {
	if (document.getElementById("modalChoice").style.display == "none") {
		document.getElementById("modalChoice").style.display = "block";
	} else if (document.getElementById("modalChoice").style.display == "block") {
		document.getElementById("modalChoice").style.display = "none";
	}
	document.getElementById("modalChoice").style.transform = "translateY(20vh)";
}

//Selecciona entre abrir el modal o acceder directamente al explorador de archivos al presionar el boton de camara
function choice() {
	var isCel = /Mobi/.test(navigator.userAgent) ? 1 : 2; // 1=Dispositivo móvil
	if (isCel == 1) {
		openModal();
	} else {
		document.getElementById("files").click();
	}
}

//Permite cambiar el target de la por captura de cámara o importar archivos desde computadora.
function openCamera() {
	document.getElementById("files").setAttribute("capture", "camera");
	document.getElementById("files").click();
	document.getElementById("files").removeAttribute("capture");
}

//Elimina o inserta el texto de instrucciones
function deleteTxt() {
	if (
		imageElement.querySelectorAll("img").length != 0 &&
		imageElement.querySelector("h4") != null
	) {
		imageElement.querySelector("h4").remove();
	} else if (
		imageElement.querySelectorAll("img").length == 0 &&
		imageElement.querySelector("h4") == null
	) {
		imageElement.innerHTML =
			'<h4 style="margin:auto;font-family:Roboto;height:20%;width:100%;font-size:30px;text-align:center;font-weight:700;color:black;">Presione el icono de la camara para comenzar a subir los archivos</h4>';
		document.querySelector(".deleteModal").setAttribute("modal", "false");
		document.querySelector(".deleteModal").innerHTML = "Borrar todos";
	}
}

function borrar() {
	event.target.parentNode.parentNode.remove();
	setTimeout(() => {
		deleteTxt();
		verifyExist();
	}, 100);
}
var cleanImg = () => {
	if (imageElement.querySelectorAll("img").length > 0) {
		imageElement.querySelectorAll("img").forEach((imagen) => {
			imagen.parentNode.remove();
		});
	}
};

// Muestra las imagenes que se han ingresado
function load() {
	const files = inputElement.files;
	// Obtener todos los archivos seleccionados

	for (let i = 0; i < files.length; i++) {
		const file = files[i];
		const reader = new FileReader();
		reader.addEventListener("load", function () {
			let img = document.createElement("img");
			let innerDiv = document.createElement("div");
			let div=document.createElement("div")
			innerDiv.style.display = "flex";
			innerDiv.style.justifyContent = "start";
			innerDiv.style.color = "#000";
			innerDiv.style.fontSize = "2.5vh";
			const icon = document.createElement("i");
			icon.addEventListener("click",borrar)
			icon.classList.add("fa-solid", "fa-xmark");
			innerDiv.appendChild(icon);
			div.appendChild(innerDiv);
			img.src = reader.result;
			div.appendChild(img)
			img.classList.add("imagen");
			div.setAttribute("style", "height:min-content;width:auto;margin:10px;");
			img.setAttribute("style", "height:20vh;width:;");
			img.addEventListener("click", bigImage)
			imageElement.appendChild(div);
		});

		reader.readAsDataURL(file);
	}

	setTimeout(() => {
		deleteTxt();
		//drag();
	}, 100);
}

function setImagenData() {
	let images = document.querySelectorAll(".imagen");
	let imagesData = [];
	for (images of images) {
		imagesData.push(images.src);
	}
	return imagesData;
}

function generatePDF(images, orientation, format) {
	window.jspdf = jsPDF;
	//window.jsPDF=window.jspdf.jsPDF;
	var format = $("#papersize").val() || "letter",
		unit = "cm";
	if (format == "customize") {
		unit = $("#unit").val();
		format = "[" + $("#sheetwide").val() + ", " + $("#sheethigh").val() + "]";
	}
	var doc = new jsPDF({
		orientation: orientation,
		unit: unit,
		format: format,
	});
	images.forEach((imageData) => {
		doc.addImage(
			imageData,
			"JPEG",
			0,
			0,
			doc.internal.pageSize.width,
			doc.internal.pageSize.height
		);
		doc.addPage();
	});
	doc.deletePage(doc.getNumberOfPages());
	doc.name = $("#filePDFName").val();
	let blobPDF = new Blob([doc.output("blob")], { type: "application/pdf" });
	let blobURL = URL.createObjectURL(blobPDF);
	return blobURL;
}

// Crea y descarga el pdf para previsualizarlo
function downloadPdf() {
	var orientation = $("#paperorientation").val(),
		images = setImagenData(),
		fname = $("filePDFName").val(),
		format = $("#papersize").val() || "letter",
		unit = "cm";
	(f1 = /^[^\\/:\*\?"<>\|]+$/), // f1=Caracteres no válidos \ | / : * ? " < >
		(f2 = /^\./); // f2=No inicie con punto (.);;
	//Convierte el arraybufer en un archivo descargable
	window.jsPDF = window.jspdf.jsPDF;
	if (!/(.)+(\.pdf)$/i.test(fname))
		// Verifica/inserta la existencia de la extensión ".pdf"
		fname = fname + ".pdf";
	if (!(f1.test(fname) && !f2.test(fname))) {
		swal(i18n("msg_error"), i18n("err_enter_filename"), "error");
		return;
	}
	if (format == "customize") {
		unit = $("#unit").val();
		format = "[" + $("#sheetwide").val() + ", " + $("#sheethigh").val() + "]";
	}
	var doc = new jsPDF(orientation, unit, format);
	images.forEach((imageData) => {
		doc.addImage(
			imageData,
			"JPEG",
			0,
			0,
			doc.internal.pageSize.width,
			doc.internal.pageSize.height
		);
		doc.addPage();
	});
	doc.deletePage(doc.getNumberOfPages());
	doc.save(name);
}

function imagenNull() {
	if (imageElement.querySelectorAll("img").length == 0) {
		return false;
	} else {
		return true;
	}
}

function errorNoImage() {
	imageElement.innerHTML =
		'<h4 style="margin:auto;font-family:Roboto;height:20%;width:100%;font-size:30px;text-align:center;font-weight:700;color:#d62424;">Por favor seleccione por lo menos una imagen</h4>';
	setTimeout(() => {
		imageElement.innerHTML =
			'<h4 style="margin:auto;font-family:Roboto;height:20%;width:100%;font-size:30px;text-align:center;font-weight:700;color:black;">Presione el icono de la camara para comenzar a subir los archivos</h4>';
	}, 3000);
}

function clearTemp() {
	$('input[name^="fileuploadx_"]').each(function (i) {
		$(this).val("");
	});
	$.ajax({
		type: "POST",
		url: ctx + "/deleteTempPath.jet",
		async: false,
		success: function (data) {
			if (!data) console.log("Not cleared");
		},
	});
}

$("#paperorientation").on("click change", function () {
	if (this.value == "p") $("#orientation").removeClass("p_landscape");
	else $("#orientation").addClass("p_landscape");
});

$("#orientation").on("click", function () {
	var o = $("#paperorientation").val();
	if (o == "p") {
		$("#orientation").addClass("p_landscape");
		$("#paperorientation").val("l");
	} else {
		$("#orientation").removeClass("p_landscape");
		$("#paperorientation").val("p");
	}
});

$("#uploadpdf").on("click", function () {
	if (imagenNull()) {
		$("#uploadpdf-modal").modal("toggle");
	} else {
		errorNoImage();
	}
	var param = "";
	$('input[name^="fileuploadx_"]').each(function (i) {
		param += "&" + $(this).attr("name") + "=" + $(this).val();
	});
});

$("#papersize").on("click change", function () {
	if ($(this).val() == "customize") $(".customsize").show();
	else $(".customsize").hide();
});

$(document).ready(function () {
	$(".modal-demo").on("hide.bs.modal", function () {
		clearTemp();
	});
});

function deleteImagesModal() {
	let modal = event.target.getAttribute("modal");
	let images = document.querySelectorAll(".imagen");
	if (modal == "false" && images.length != 0) {
		event.target.innerHTML = `<div>Confirma la accion</div>
									<div>
										<button style="all:unset; margin:5px; background-color:#F05050; border-radius:5px; padding:8px;" onclick="cancel('Borrar todos')">Cancelar</button>
										<button style="all:unset; margin:5px; background-color:#5FBEAA; padding:8px; border-radius:5px;" onclick="deleteImages()">Eliminar todos</button>
									</div>`;
		event.target.setAttribute("modal", "true");
	}
}

function cancel(txt) {
	event.target.parentNode.parentNode.setAttribute("modal", "false");
	event.target.parentNode.parentNode.innerHTML = txt;
}

function deleteImages() {
	let images = document.querySelectorAll(".imagen");
	images.forEach((element) => {
		element.remove();
	});
	event.target.parentNode.parentNode.setAttribute("modal", "false");
	event.target.parentNode.parentNode.innerHTML = "Borrar";
	deleteTxt();
	verifyExist();
}
const verifyExist = () => {
	let docs = document.querySelectorAll(".docsLoad");
	if (docs.length != 0) {
		docs.forEach((element) => {
			let value = element.getAttribute("value").replace(/\s+/g, "");
			console.log(value);
			let images = document.querySelectorAll(`.${value}`);
			if (images.length != 0) {
				element.style.backgroundColor = "#848";
				element.setAttribute("agregado", true);
			} else {
				element.style.backgroundColor = "#5FBEAA";
				element.setAttribute("agregado", false);
			}
		});
	}
};

function verifyImages(clean = false) {
	setTimeout(() => {
		if (clean) {
			cleanImg();
		}
		if (imagenesEdit.length != 0) {
			for (let i = 0; i < imagenesEdit.length; i++) {
				let img = document.createElement("img");
				let div=document.createElement("div") 
				const innerDiv = document.createElement("div");
				innerDiv.style.display = "flex";
				innerDiv.style.justifyContent = "start";
				innerDiv.style.color = "#000";
				innerDiv.style.fontSize = "2.5vh";
				const icon = document.createElement("i");
				icon.addEventListener("click",borrar)
				icon.classList.add("fa-solid", "fa-xmark"); // Asegúrate de que la clase "fa-xmark" esté definida correctamente
				innerDiv.appendChild(icon);
				div.appendChild(innerDiv);
				div.appendChild(img)
				img.setAttribute("src", imagenesEdit[i]);
				div.setAttribute("style", "height:min-content;width:auto;margin:10px;");
				img.setAttribute("style", "height:20vh;width:;");
				img.addEventListener("click", bigImage)
				img.classList.add("imagen");
				imageElement.appendChild(div);
				if (
					imageElement.querySelector(".loader") &&
					imageElement.querySelector(".loader") != null
				) {
					imageElement.querySelector(".loader").remove();
				}
			}

			setTimeout(() => {
				deleteTxt();
				verifyExist();
			}, 800);
			//for(leti=0;i<)
		}
	}, 500);
}
const verificacion = () => {
	let dropz = document.querySelector(".dropzone")
	let docs = dropz.querySelectorAll(".link");
	if (docs.length != 0) {
		return docs;
	} else {
		return false;
	}

	//}
};

//Esta funcion convierte el pdf local en imagenes
async function pdfForEdit(id) {
	if (imageElement.querySelector("h4")) {
		imageElement.querySelector("h4").remove();
	}
	if (imageElement.querySelectorAll("img").length == 0) {
		imageElement.innerHTML =
			"<div class='loader'><div class='loaderDiv'><div class='loader2'>Loading...</div></div></div>";
	}
	inputFile = document.getElementById(id);
	// Obtener el archivo PDF del input tipo file
	const pdfFile = inputFile.files[0];

	if (!pdfFile) {
		console.error("No se seleccionó ningún archivo PDF");
		return;
	}

	// Leer el archivo PDF como ArrayBuffer
	const pdfData = await readFileAsArrayBuffer(pdfFile);

	// Cargar el PDF con pdf.js
	try {
		const pdfDoc = await pdfjsLib.getDocument({ data: pdfData }).promise;
		const options = {
			type: "png", // Tipo de imagen (png, jpeg, etc.)
		};

		const images = []; // Array para almacenar las imágenes

		// Crear una función asincrónica para procesar cada página
		async function processPage(pageNumber) {
			const page = await pdfDoc.getPage(pageNumber);

			// Crear un lienzo HTML para la imagen
			const canvas = document.createElement("canvas");
			const context = canvas.getContext("2d");

			// Configurar el tamaño del lienzo según la página del PDF
			const viewport = page.getViewport({ scale: 1.5 });
			canvas.width = viewport.width;
			canvas.height = viewport.height;

			// Renderizar la página en el lienzo
			const renderContext = {
				canvasContext: context,
				viewport: viewport,
			};

			await page.render(renderContext).promise;

			// El lienzo ahora contiene la página del PDF como una imagen
			const imgDataUrl = canvas.toDataURL(options.type);
			images.push(imgDataUrl);
		}

		// Recorrer las páginas del PDF de forma secuencial
		for (let pageNumber = 1; pageNumber <= pdfDoc.numPages; pageNumber++) {
			await processPage(pageNumber);
		}

		// Una vez que se hayan procesado todas las páginas, puedes hacer algo con el array de imágenes
		imagenesEdit = images;

		// Llamar a verifyImages después de procesar todo el PDF
		verifyImages();
	} catch (err) {
		if (
			imageElement.querySelector(".loader")
		  ) {
			imageElement.querySelector(".loader").remove();
		  }
		imageElement.innerHTML=		'<h4 style="margin:auto;font-family:Roboto;height:20%;width:100%;font-size:30px;text-align:center;font-weight:700;color:red;">!! ERROR No se ha podido acceder al archivo</h4>';
		setTimeout(() => {
		 imageElement.innerHTML='<h4 style="margin:auto;font-family:Roboto;height:20%;width:100%;font-size:30px;text-align:center;font-weight:700;color:black;">Presione el icono de la camara para comenzar a subir los archivos</h4>';
		}, 3000);
	}
}

// Función para leer un archivo como ArrayBuffer
function readFileAsArrayBuffer(file) {
	return new Promise((resolve, reject) => {
		const reader = new FileReader();
		reader.onload = (event) => {
			resolve(event.target.result);
		};
		reader.onerror = (error) => {
			reject(error);
		};
		reader.readAsArrayBuffer(file);
	});
}

const modalPdfLoads = () => {
	let container = event.target;
	let docs = verificacion();
	container = container.parentNode.parentNode;
	let modalContent = "<div>";

	docs.forEach((element) => {
		modalContent += `<div class="docsLoad" agregado="true" style="all:unset; margin:5px; background-color:#5FBEAA; padding:8px; border-radius:5px;" value="${element
			.getAttribute("title")
			.slice(0, -4)}" onclick="pdfToImage('${element.getAttribute(
				"href"
			)}', false)">${element.getAttribute("title").slice(0, -4)}</div>`;
	});

	modalContent += `<button style="all:unset; margin:5px; background-color:#F05050; border-radius:5px; padding:8px;" onclick="pdfLoads()">Cancelar</button>`;
	modalContent += "</div>";

	container.innerHTML = modalContent;
};

//Esta funcion permite editar el modal de agregar pef
var aggPdf = (loads = false) => {
	let modal = event.target.getAttribute("modal");
	if (modal == "false") {
		let docs = verificacion();
		if (docs != false && loads == false) {
			event.target.innerHTML = `
			<div>Confirma la accion</div>
			<div>
				<button style="all:unset; margin:5px; background-color:#5FBEAA; padding:8px; border-radius:5px;" onclick="modalPdfLoads()">Subidos</button>
				<input style="display:none;" type="file" id="pdfForEdit" onchange='pdfForEdit("pdfForEdit")'></input>
				<label for="pdfForEdit" style="all:unset; margin:5px; background-color:#888; padding:8px; border-radius:5px;">Locales</label>
				<button style="all:unset; margin:5px; background-color:#F05050; border-radius:5px; padding:8px;" onclick="cancel('Agregar PDF...')">Cancelar</button>
			</div>`;
			event.target.setAttribute("modal", "true");
		} else if (loads == true) {
		} else {
			event.target.innerHTML = `
		<div>Confirma la accion</div>
		<div>
			<input style="display:none;" type="file" id="pdfForEdit" onchange='pdfForEdit("pdfForEdit")'></input>
			<label for="pdfForEdit" style="all:unset; margin:5px; background-color:#888; padding:8px; border-radius:5px;">Locales</label>
			<button style="all:unset; margin:5px; background-color:#F05050; border-radius:5px; padding:8px;" onclick="cancel('Agregar PDF...')">Cancelar</button>
		</div>`;
			event.target.setAttribute("modal", "true");
		}
	}
};
const closeImg = () => {
	let modal = document.querySelector("#modalImage");
	modal.style.display = "none";
};
const bigImage = () => {
	let image = event.target.getAttribute("src");
	let modal = document.querySelector("#modalImage");
	modal.innerHTML = `<div><img src=${image}></img><i onclick="closeImg()" class="fa-solid fa-xmark"></i></div>`;
	modal.style.display = "block";
};





