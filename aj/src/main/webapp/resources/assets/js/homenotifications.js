
// let datos;
// function getNotify() {
//   fetch(ctx + "/getHomeNotifications", {
//       headers: {
//           "Content-Type": "application/json;charset=UTF-8"
//       }
//   })
//   .then(response => response.json())
//   .then(data => datos=data)
//   .catch(error => console.error(error));
// };

// let objetofilter={};
// function objetoFiltrado(datos){
// 	Object.values(datos).forEach(objeto => {
// 	  if (objetofilter[objeto.proceedings]) {
// 	    // Si ya hay un objeto en el objeto agrupado con la misma clave, agregamos el objeto actual a un array
// 	    objetofilter[objeto.proceedings].push(objeto);
// 	  } else {
// 	    // Si no hay un objeto en el objeto agrupado con la misma clave, creamos un array con el objeto actual
// 	    objetofilter[objeto.proceedings] = [objeto];
// 	  };
// 	});
// };

// function createNewElement(tag,txt,attribute,value){
// 	txt=txt||'';
// 	let element=document.createElement(tag);
// 	element.innerHTML=txt;
// 	element.setAttribute(attribute,value);
// 	return element;
// };

// function writeNotify(){
// 	let contenedor=document.querySelector("tbody");
// 	if(contenedor.childElementCount>0){
// 	  contenedor.innerHTML=''
// 	}

// 	Object.values(objetofilter).forEach(objeto=>{
// 	  let id=objeto[0].proceedings;
// 	  let cliente=objeto[0].clientname;
// 	  let juzgado = objeto[0].courtname !== '' ? objeto[0].courtname : 'Juzgado no asignado';
// 	  let ultimaFecha=objeto[0].date;
// 	  let tr=createNewElement("tr");
// 	  let tdExp=(createNewElement("td", "Documento <br>"));
// 	  let tdClie=(createNewElement("td",cliente));
// 	  let tdJuz=(createNewElement("td", juzgado));
// 	  let tdDate=(createNewElement("td", ultimaFecha));
// 	  let tdButtDesp=createNewElement("td",`<i class="fa-sharp fa-solid fa-chevron-up " style="transform:rotate(180deg);transition: all 1s;"></i>`, "class",`a${objeto[0].proceedings.replace(/[^a-zA-Z0-9]/g, '')}`);
// 	  let tdButtAll=createNewElement("td",`<i class="fa-solid fa-check-to-slot" onclick=notifyReadAll(${objeto[0].idref})></i>`,"style","min-width: max-content;");
// 	  let a=createNewElement("a", id, 'href',`${objeto[0].link}?language=es&rid=${objeto[0].idref}&nid=${objeto[0].notificationid}`,'style',"font-size","15%");
// 	  tdButtDesp.addEventListener('click',e=>{abrirColapse(objeto[0].proceedings)});
// 	  tdExp.appendChild(a);
// 	  tr.appendChild(tdExp);
// 	  tr.appendChild(tdJuz);
// 	  tr.appendChild(tdClie);
// 	  tr.appendChild(tdDate);
// 	  tr.appendChild(tdButtDesp);
// 	  tr.appendChild(tdButtAll);
// 	  contenedor.appendChild(tr);
// 	  var table=createNewElement("table","","style", "background-color:#62BFAB; color:#000;");//,"","class","table table-striped table-bordered;");
// 	  let tr1=createNewElement("tr");
// 	  let tdDesc=createNewElement("td","Descripcion");
// 	  let tdMov=createNewElement("td","Movimiento");
// 	  let tdDat=createNewElement("td","Fecha");
// 	  let tdInf=createNewElement("td", `Ver informacion`);
// 	  let tdButOne=createNewElement("td","Eliminar notificacion");
// 	  let thead=createNewElement("thead","","style","background-color:#FFFFFF;");
// 	  tr1.appendChild(tdDesc);
// 	  tr1.appendChild(tdMov);
// 	  tr1.appendChild(tdDat);
// 	  tr1.appendChild(tdInf);
// 	  tr1.appendChild(tdButOne);
// 	  let td2=createNewElement("td","","colspan",6);
// 	  td2.setAttribute("style","display:none;  transition-property: display;transition-duration: 2s;");
// 	  thead.appendChild(tr1);
// 	  table.appendChild(thead);
// 	  table.classList.add("table","table-bordered");
// 	  //contenedor.appendChild(table)
// 	  td2.appendChild(table);
// 	  td2.id=objeto[0].proceedings;
// 	  contenedor.insertRow(td2);
// 	  var tbody=createNewElement("tbody");
// 	  objeto.forEach(element=>{
// 	    let tr=createNewElement("tr","","style","color:#000;");
// 	    let descripcion=element.description;
// 	    let movimiento=element.modulename;
// 	    let fecha=element.date;
// 	    let tdInf=createNewElement("td", `<i class="fa-solid fa-circle-info" onclick=infonotify(${element.notificationid})></i>`, "style","text-align:center;");
// 	    let tdDesc=createNewElement("td",descripcion, "id",`${element.notificationid}`);
// 	    tdDesc.classList.add("expediente");
// 	    let tdMov=createNewElement("td",movimiento);
// 	    let tdDat=createNewElement("td",fecha);
// 	    let tdButOne=createNewElement("td",`<i class="fa-sharp fa-solid fa-square-check" onclick=notifyAsRead(${element.notificationid})></i> `,"style","text-align: center; ");
// 	    tr.appendChild(tdDesc);
// 	    tr.appendChild(tdMov);
// 	    tr.appendChild(tdDat);
// 	    tr.appendChild(tdInf);
// 	    tr.appendChild(tdButOne);
// 	    tbody.appendChild(tr);
// 	  });
// 	  table.appendChild(tbody);
// 	});
// };


function abrirColapse(id){
	
};

function notifyReadAll(id){
  let ids=[];
  let container=document.getElementById(id);
  let tds=container.querySelectorAll(".expediente");
  tds.forEach(element=>{ids.push(element.id)});
  swal({
		title:i18n('msg_are_you_sure'),
		text:i18n('msg_notificatios_as_read'),
		type:"warning",
		showCancelButton:true,
		confirmButtonClass:'btn-warning',
		confirmButtonText:i18n('btn_yes_remove_it'),
		closeOnConfirm:false,
		closeOnCancel:false
	},function(isConfirm){
	    ids.forEach(element=>{	
	      $.ajax({
			type:"POST",
			url:ctx+'/notifyAsRead',
			data:'id='+element,
			async:false,
			success:function(data){
				if(data)
					console.log(event.target.parentNode.parentNode);
				else
					swal(i18n('msg_warning'),i18n('err_notification_fail')+e,'error');
			},error:function(e){
				swal(i18n('msg_warning'),i18n('err_notification_fail')+e,'error');
			}
		});
      });
	});
};

let numberOfNotify;
function getNumberNotify() {
  fetch(ctx + "/getHomeNotifications", {
      headers: {
          "Content-Type": "application/json;charset=UTF-8"
      }
  })
  .then(response => response.json())
  .then(data => {
      let bell = document.getElementById("bellNotify");
      let circle = document.getElementById("notifyCircle");
      if (Object.keys(data).length > 0) {
          if (circle) {
              // Si el círculo ya existe, actualiza su contenido
              circle.innerHTML = Object.keys(data).length;
          } else {
              // Si el círculo no existe, créalo
              circle = document.createElement("span");
              circle.setAttribute("id", "notifyCircle");
              circle.setAttribute("style", `
                  display: inline-flex;
                  align-items: center;
                  justify-content: center;
                  border-radius: 50%;
                  text-align: center;
                  line-height: inherit;
                  background-color: red;
                  color: white;
                  width: 2vh;
                  height: 2vh;
                  font-size: 1vh;
              `);
              circle.innerHTML = Object.keys(data).length;
              bell.appendChild(circle);
          }
      } else {
          // Si no hay notificaciones, elimina el círculo existente (si lo hay)
          if (circle) {
              bell.removeChild(circle);
          };
      };
  })
  .catch(error => console.error(error));
};

// function getNotify() {
//   fetch(ctx + "/getHomeNotifications", {
//       headers: {
//           "Content-Type": "application/json;charset=UTF-8"
//       }
//   })
//   .then(response => response.json())
//   .then(data => {
//     objetofilter={}
//     objetoFiltrado(data)
//    // writeNotify()
//     getNumberNotify()
//   })
//   .catch(error => console.error(error));
// };

// window.addEventListener("load", e=>{
// 	getNotify();
// 	//setInterval(() => {
// 		getNotify();
// //		console.log("notificaciones actualizadas");
// 	//}, 50000);
// });

