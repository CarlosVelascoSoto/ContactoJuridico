<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<link rel="stylesheet" type="text/css" href="resources/assets/css/font-awesome.min.css">
<!--link rel="stylesheet" type="text/css" href="resources/assets/plugins/dropzone/basic.css"-->
<!--link rel="stylesheet" type="text/css" href="resources/assets/plugins/dropzone/dropzone.css"-->
<link rel="stylesheet" type="text/css" href="resources/local/general/cam/cam.css">
<!-- script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.10.377/pdf.min.js"></script> -->
<script src="resources/assets/plugins/jsPDF/pdf.min.js"></script>

<style>
/* HTML: <div class="loader"></div> */
.loader {
    width: 30vw;
    height: 30vw;
    border: 5px solid #FFF;
    border-bottom-color: #000;
    border-radius: 50%;
    display: flex;
	align-items: center;
	justify-content: center;
    animation: rotation 1s linear infinite;
    }
	.loader2{
		width: 20vw;
		height: 20vw;
		color: #000;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 4vw;
		animation:rotationInv 1s linear infinite;
	}
	@keyframes rotationInv {
		0%{transform: rotate(0deg);}
		100%{transform: rotate(-360deg);}
	}
    @keyframes rotation {
    0% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(360deg);
    }
    } 
	#modalImage{
		z-index: 1000;
		left: 50%;
		top:50%;
  		transform: translate(-50%, -50%);
		width: 100%;
		height: 100%;
		padding: 0% !important;
	}
	#modalImage div{
		width: 100%;
		height: 100%;
		display: flex;
		padding: 10px;
		justify-content: center;
	}
	#modalImage div>img{
		width: auto;
		height: auto;
		
	}
	#modalImage div>i{
	width: 2.5vw;
	position: absolute;
	height: 2.5vw;
    font-size: 2vw;
	display: flex;
	align-items: center;
	justify-content: center;
	background-color: #fff;
	color: #000;
	margin: 1vw 2vh 0 0 ;
	border:solid 2px #000;
	border-radius: 50%;
}
@media(max-width:950px){
	#modalImage div>i{
	width: 5vh;
	height: 5vh;
    font-size: 4vh;

}
}
</style>

<section id="camcontainer">
	<div id="modalImage" style="display: none; z-index: 100000; position: absolute;" class="container fullscreen"></div>
	<div id="camdesktop" class="container fullscreen">
      <div class="col-xs-12 tac" style="padding:0;">
        <div id="cameraRoll"></div>
      </div>
	  <div class="col-xs-12 boxpreview">
	    <button type="button" class="btn-closepreview trn2ms asLink" title="Presiona aquí para iniciar la captura de im&aacute;genes"
	      onclick="$('.boxpreview').toggle();$('#camdesktop').css('z-index',1);">&times;</button>
	    <img id="preview1">
	  </div>
	</div>
	<div class="container-btncapture">
	  <input type="file" accept="image/*" id="files" style="display:none" multiple>
	  <button type="button" id="buttonCam"class="btn-capture trn2ms" title="Presiona aquí para iniciar la captura de im&aacute;genes"
	  	onClick="choice()"><i class="material-icons">camera_alt</i>
	  </button>
	  <button type="button" id="uploadpdf" title="Generar PDF y subir a la nube" class="trn2ms">
	  	<i class="material-icons">cloud_upload</i>
	  </button>
	  
		<!--div id="cam-modal" class="modal-demo">
			<form id="formcam">
				<div class="row">
					<div id="areaCamUpload"><span class="textContent"><fmt:message key='label.dropzone' /></span>
						<div id='uploadCamdiv' class="dz-default dz-message file-dropzone text-center well col-sm-12"></div>
					</div>
				</div>
			</form>
		</div-->
		<div value="true" class="deleteModal" style="margin:10px; background-color: #444; color:#fff; padding: 10px; border-radius: 10px; width: max-content; user-select: none; cursor: pointer;" modal="false" onclick="deleteImagesModal()">Borrar todos</div>
		<div style="margin:10px; background-color: #444; color:#fff; padding: 10px; border-radius: 10px; width: max-content; user-select: none; cursor: pointer;" modal="false" onclick="aggPdf()">Agregar PDF...</div>
	</div>
	
	<div id="uploadpdf-modal" class="modal fade" role="dialog" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true" > &times;</button>
	        <h4 class="modal-title capitalize"><fmt:message key="text.cam" /> <span id="camId"></span></h4>
	      </div>
	      <div class="modal-body p-0">
	        <form id="formuploadpdf">
	          <div class="row">
	            <div class="col-xs-12">
	              <div class="form-group inlineflex w100p">
	                <label for="papersize" class="supLlb"><fmt:message key='text.sheetsize' /></label>
	                <select id="papersize" class="ddsencillo c39c">
	                  <option value="letter" selected><fmt:message key='text.letter' /> / A (21.59cm x 27.94cm / 8.5" x 11")</option>
	                  <option value="legal">Legal (21.59cm x 35.56cm / 8.5" x 14")</option>
	                  <option value="a3">A3 (29.70cm x 42.00cm / 11.69" x 16.54")</option>
	                  <option value="a4">A4 (21.00cm x 29.70cm / 8.3" x 11.7")</option>
	                  <option value="a5">A5 / Ofuku Hagaki (14.80cm x 20.00cm / 5.8" x 7.9")</option>
	                  <option value="a6">A6 / Hagaki / PC (10.00cm x 14.80cm / 3.94" x 5.83")</option>
	                  <option value="b6">B6 / 5R / 2L (12.70cm x 17.78cm / 5.0" x 7.0")</option>
	                  <option value="b7">B7 / Enprint / 3R / L / Enprint (8.89cm x 12.70cm / 3.5" x 5.0")</option>
	                  <option value="dl">DL (9.90cm x 21.00cm / 3.9" x 8.3")</option>
	                  <option value="government-letter"><fmt:message key='text.govermentletter' /> (20.32cm x 26.67cm / 8.0" x 10.5")</option>
	                  <option value="junior-legal">Junior Legal (12.70cm x 20.32cm / 5.0" x 8.0")</option>
	                  <option value="ledger">Ledger / <fmt:message key='text.tabloid' /> / B (27.94cm x 43.18cm / 11.0" x 17.0")</option>
	                  <option value="tabloid"><fmt:message key='text.tabloid' /> (43.18cm x 27.94cm / 17.0" x 11.0")</option>
	                  <option value="credit-card"><fmt:message key='text.creditcard' /> (5.5cm x 9.1cm / 2.17" x 3.58")</option>
	                  <option value="customize"><fmt:message key='text.customize' />...</option>
	                </select>
	              </div>
	              	<div class="row customsize">
	              		<div class="col-xs-12 col-sm-6">
		            		<div class="form-group inlineflex w100p">
				                <label for="unit" class="supLlb"><fmt:message key='text.sheetsize' /></label>
				                <select id="unit" class="ddsencillo c39c">
				                  <option value="mm" selected>(mm)&emsp;<fmt:message key='text.millimeters' /></option>
				                  <option value="cm">(cm)&emsp;<fmt:message key='text.centimeters' /></option>
				                  <option value="in">(in)&emsp;<fmt:message key='text.inches' /></option>
				                  <option value="pt">(pt)&emsp;<fmt:message key='text.points' /></option>
				                  <option value="px">(px)&emsp;<fmt:message key='text.pixels' /></option>
				              	</select>
			              	</div>
	             		</div>
		           		<div class="col-xs-12 col-sm-3">
		           			<div class="form-group w100p inlineflex m-0">
								<label for="sheetwide" class="supLlb"><fmt:message key='text.wide' /></label>
								<input type="number" class="form-control c39c" id="sheetwide" value="3" min="1" autocomplete="off">
							</div>
						</div>
						<div class="col-xs-12 col-sm-3">
		           			<div class="form-group w100p inlineflex m-0">
								<label for="sheethigh" class="supLlb"><fmt:message key='text.high' /></label>
								<input type="number" class="form-control c39c" id="sheethigh" value="4" min="1" autocomplete="off">
							</div>
						</div>
	             	</div>
	            </div>
	          </div>
	          <div class="row">
	            <div class="col-xs-11">
	              <div class="form-group inlineflex w100p">
	                <label for="paperorientation" class="supLlb"><fmt:message key='text.sheetorientation' /></label>
	                <select id="paperorientation" class="ddsencillo c39c">
	                  <option value="p" selected><fmt:message key='text.portrait' /></option>
	                  <option value="l"><fmt:message key='text.landscape' /></option>
	                </select>
	              </div>
	            </div>
	            <div class="col-xs-1">
	            	<i class="fa fa-file-o" id="orientation"></i>
	            </div>
	            
	            <div class="col-xs-12">
					<div class="form-group inlineflex w100p">
						<label for="filePDFName" class="supLlb"><fmt:message key='text.filename' /></label>
						<input type="text" class="form-control c39c" id="filePDFName"
							placeholder="<fmt:message key='text.documentname' />"
							value="<fmt:message key='text.newdoc' />.pdf" autocomplete="off">
					</div>
				</div>
	          </div>
	        </form>
	      </div>
	
	      <div class="modal-footer">
	        <button type="button" id="previewpdf" class="btn btn-primary waves-effect waves-light" onclick="downloadPdf()">Vista previa</button>
	        <button type="button" id="startupload" class="btn btn-default waves-effect waves-light" onclick="verifyNames();">Aceptar</button>
	        <button type="button" onclick="$('#uploadpdf-modal').modal('hide');" class="btn btn-danger waves-effect waves-light m-l-10">Cancelar</button>
	      </div>
		<input type="hidden" id="targetDZ">
	    </div>
	  </div>
	</div>

	<!--Imagenes cargadas -->
	<div id="sectionImg" style=" display: flex; flex-wrap: wrap; justify-content: space-evenly; position:absolute;margin:auto;left:5%;right:0;top:0;bottom:0; width: 90%; max-height: 80vh;
	overflow: auto; ">
	  <h4 id="instruccion" style="margin: auto; font-family: Roboto;height: 20%; width: 100%; font-size: 30px; text-align: center; font-weight: 700; color: black;">Presione el icono de la camara para comenzar a subir los archivos</h4>
	</div>
	  
	<!--Modal para seleccionar si tomar una foto o subir un archivo-->
	<div id="modalChoice" style="position:absolute;margin:auto;left:0;right:0;top:0;bottom:0; display: none; width: 50%; height: 30vh; background-color: #212121; border-radius: 10px; border: solid .2rem #444; padding: 10px; transform: translateY(60vh);">
	  <div style="display: flex; flex-direction: column; border-radius: 10px; width: 100%; height: 100%; justify-content: space-evenly;"> 
	    <h4 style="font-size: 3rem; color: #fff; text-align: center;">Seleciona el origen de la imagen</h4>      
	    <div style="display: flex; justify-content: space-around;flex-wrap: wrap;"> 
	      <button value="cam" onclick="openCamera()" class="sel" style="all: unset;font-size: 2rem; min-width: 80px; padding: 5px;cursor:pointer; margin:5px; color: #fff;" > Abrir camara  <i class="fa-solid fa-camera"></i></button>
	      <button value="up" onclick="{document.getElementById('files').click()}" class="sel" style=" all: unset; font-size: 2rem; cursor:pointer; min-width: 80px; padding: 5px; margin:5px; color:#fff ;" >Subir archivos  <i class="fa-solid fa-file"></i></button>
	    </div>
	  </div>
	</div>
</section>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.10.377/pdf.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/sortablejs@latest/Sortable.min.js"></script>
<script>var ctx="${pageContext.request.contextPath}"</script>
<script src="https://kit.fontawesome.com/447df55f6c.js" crossorigin="anonymous"></script>
<!--script src="resources/assets/plugins/dropzone/dropzoneImg.js"></script>
<script src="resources/assets/js/customDropZoneImg.js"></script-->
<script defer src="resources/assets/plugins/jsPDF/jspdf.umd.min.js" crossorigin="anonymous" referrerpolicy="no-referrer"
	integrity="sha512-qZvrmS2ekKPF2mSznTQsxqPgnpkI4DNTlrdUmTzrDgektczlKNRRhy5X5AAOnx5S09ydFYWWNSfcEqDTTHgtNA=="></script>
<script src="resources/local/general/cam/cam.js"></script>