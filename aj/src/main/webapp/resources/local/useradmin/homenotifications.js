;function notifyReadAll(id){
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
  }).then((isConfirm) => {
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