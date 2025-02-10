;$("#roleList").change(function(){
	var selRole=$('#roleList option:selected').val();
	if(selRole!='0'){
		$.ajax({
			type:"POST",
			url:ctx+"/privileges",
			data:"srl="+selRole,
			async:false,
			success:function(data){
				window.location=ctx+"/privileges"+"?language="+getLanguageURL()+"&srl="+selRole;
			},error:function(data){
				swal(i18n("msg_error"),i18n("err_unable_get_role")+" (2)","error");
			}
		});
	}
});

function saveNewRole(e){
	e.disabled=true;
	var rolename=$("#rolename").val();
	if(rolename==''){
		$('#putError').html(i18n("err_role_empty"));
		$('#addRoleError').css('display','block');
		e.disabled=false;
		return false;
	}
	var param="rolename="+rolename+"&firm="+$('#firm').val();
	$.ajax({
		type:"POST",
		url:ctx+"/addrole",
		data:param,
		async:false,
		success:function(data){
			if(data=="True"){
				swal(i18n("msg_success"),i18n("msg_role_saved_successfully"),"success");
				location.href=location.pathname+"?language="+ getLanguageURL();
			}else{
				$('#putError').html(i18n("err_role_exists"));
				$('#addRoleError').css('display','block');
				e.disabled=false;
			}
		},error:function(er){e.disabled=false;swal(i18n("msg_error"),i18n("err_unable_get_role")+", err.1","error");}
	});				
};

$("#addNewRoleCancel").click(function(){
	$('#addRoleError').css('display','none');
});

function editPrivilege(id){
	var param="id="+id+"&role="+$('#roleList').val();
	$.ajax({
		type:"POST",
		url:ctx+"/editprivilege",
		data:param,
		async:false,
		success:function(data){
			if(data==''){
				swal(i18n("msg_error"),i18n("err_unable_get_privilege")+" (1)","error");
			}else{
				misPermisos = data[0];
				menu = data[1];
				allpermisos = data[2];
				
				var html = '';
				var existe = '';
				var idx='';
				for (var i = 0; i < allpermisos.length; i+=1) {
					existe = '';
					for (var n = 0; n < misPermisos.length; n+=1) {
						if(misPermisos[n].privilegesid==allpermisos[i].privilegesid){
							existe = 'checked';
							break;
						}
							
					}
				  idx = 'mp_'+allpermisos[i].privilegesid;
				  html += '<div class="form-group"><label class="switch">'+
						  '<input type="checkbox" class="form-control" id="'+idx+'" name="'+
						  idx+'" onclick="checks(\''+idx+'\');" '+existe+'/><span class="slider round"></span></label>'+
						  '<label for="'+idx+'" class="switch"><span class="sr-only b-chk">'+
						  allpermisos[i].privilege+'</span></label></div>';
				}
				$('#div_edit_permiso').html(html);
				$('#editdataid').val(menu.menuid);
				$('#roleid').val($('#roleList').val());
				$('#editRoleTitle').text(i18n('msg_role_title'));
				$('#editRoleName').text($("#roleList option:selected" ).text());
				$('#EditPrivilegeError').css('display','none');
				checks(idx);
			}
		},error:function(data){swal(i18n("msg_error"),i18n("err_unable_get_privilege")+" (2)","error");}
	});
};

function deleteRoleDetails(roleId,rolename){
	$('#delRoleName').text(rolename);
	$('#deleteRoleButton').attr('onclick','deleteRole('+roleId+')');
};

function deleteRole(roleId){
	var param="roleid="+roleId;
	$.ajax({
		type:'POST',
		url:ctx+"/deleterole",
		data:param,
		async:false,
		success:function(resp){
			window.location=ctx+"/roles"+"?language="+getLanguageURL();
		},error:function(resp){swal(i18n("msg_error"),i18n("err_unable_get_role")+", err.2","error");}
	});
};

function getRoleDetails(id){;
	$.ajax({
		type:"POST",
		url:ctx+"/getRoleDetails",
		data:"id="+id,
		async:false,
		success:function(data){
			var info=data[0]||[];
			if(info.length>0){
				$('#EditRoleError').css('display','none');
				$('#editRoleId').val(info[0].roleid);
				$('#editRoleName').val(info[0].rolename);
				$('#editFirm').val(info[0].companyid==null?'0':info[0].companyid);
			}else{
				$('#EditRoleError').css('display','block');
				$('#putEditRoleError').html(i18n('err_unable_get_role'));
			}
		},error:function(e){
			console.log("Error al obtener rol. "+e);
		}
	});
};

function updateEditRole(){
	var roleId=$("#editRoleId").val(),rolename=$("#editRoleName").val(),
		firm=$("#editFirm").val();
	var param="roleid="+roleId+"&rolename="+rolename+"&firm="+firm;	
	if(rolename==''){
		$('#putEditRoleError').html(i18n("err_enter_rolename"));
		$('#EditRoleError').css('display','block');
		return false;
	}else{
		$.ajax({
			type:'POST',
			url:ctx+'/updaterole',
			data:param,
			async:false,
			success:function(resp){
				window.location=ctx+"/roles"+"?language="+getLanguageURL();
			},error:function(e){swal(i18n("msg_error"),i18n("err_unable_get_role")+", err.3","error");}
		});
	}	
};

function updateEditPrivilege(){
	var form = $('#formeditpermisos');
	$.ajax({
		type:'POST',
		url:ctx+'/updateprivilege',
		data:form.serialize(),
		async:false,
		success:function(resp){
			swal({
				title:i18n('msg_success'),
				text:i18n('msg_privilege_saved_successfully'),
				type:"success",
				timer:3000,
				allowEscapeKey:false
			},function(){
				swal.close();
			});
			var selRole=$('#roleList option:selected').val();
			window.location=ctx+"/privileges"+"?language="+getLanguageURL()+"&srl="+selRole;
		},error:function(e){
			$('#putEditPrivilegeError').html(i18n("err_unable_get_privilege")+" (3)");
			$('#EditPrivilegeError').css('display','block');
		}
	});
};

function i18n(msg){
	var i18n_message="";
	//IDIOMAS:si en la URL no contiene la variable "lenguage", asumirá que esta en español
	lang=getLanguageURL();
	if(msg=='err_char_contains_password')
		i18n_message=lang=='en'?'A password contains at least eight characters, including at least one number and one lowercase and'
			+' one uppercase letters and, at last, one special character:<br> _ . - ^ * #?! @ $ % &'
			:'Una contrase\u00f1a debe contener m\u00ednimo ocho caracteres, al menos un n\u00famero,'
			+' una letra may\u00fascula, una min\u00fascula y alguno de estos s\u00edmbolos:<br> _ . - ^ * #?! @ $ % &';
	else if(msg=='err_email_exists')
		i18n_message=lang=='en'?'Email already exists in another user!':'\u00a1El correo electr\u00f3nico ya existe en otro usuario!';
	else if(msg=='err_enter_correct_email')
		i18n_message=lang=='en'?'Please enter correct email address!':'\u00a1Favor de ingresar una direcci\u00f3n de correo electr\u00f3nico v\u00e1lida!';
	else if(msg=='err_enter_email')
		i18n_message=lang=='en'?'Please enter email!':'\u00a1Favor de ingresar un correo electr\u00f3nico!';
	else if(msg=='err_enter_firstname')
		i18n_message=lang=='en'?'Please enter firstname!':'\u00a1Favor de completar el campo de nombre(s)';
	else if(msg=='err_enter_lastname')
		i18n_message=lang=='en'?'Please enter lastname!':'\u00a1Favor de completar el campo de apellido(s)';
	else if(msg=='err_enter_password')
		i18n_message=lang=='en'?'Please enter password!':'\u00a1Favor de ingresar la contrase\u00f1a!';
	else if(msg=='err_enter_rolename')
		i18n_message=lang=='en'?'Please enter rolename!':'\u00a1Favor de ingresar el nombre del rol!';
	else if(msg=='err_enter_username')
		i18n_message=lang=='en'?'Please enter username!':'\u00a1Favor de ingresar el nombre de usuario!';
	else if(msg=='err_enter_text2search')
		i18n_message=lang=='en'?'Please enter text to search!':'\u00a1Favor de ingresar alg\u00fan texto a buscar!';
	else if(msg=='err_not_same_user_pass')
		i18n_message=lang=='en'?'The password must be different from about the user name!':'\u00a1La contrase\u00f1a debe ser distinta al nombre de usuario!';
	else if(msg=='err_role_empty')
		i18n_message=lang=='en'?'Role is empty!':'\u00a1El rol esta vac\u00edo!';
	else if(msg=='err_role_exists')
		i18n_message=lang=='en'?'Role Exists!':'\u00a1El rol ya existe!';
	else if(msg=='err_select_role')
		i18n_message=lang=='en'?'Please select a role!':'\u00a1Favor de seleccionar un rol!';
	else if(msg=='err_unable_get_privilege')
		i18n_message=lang=='en'?'Unable to get privilege record!':'\u00a1No fue posible obtener el registro del privilegio!';
	else if(msg=='err_unable_get_role')
		i18n_message=lang=='en'?'Unable to get role record!':'\u00a1No fue posible obtener el registro del rol!';
	else if(msg=='err_unable_get_user')
		i18n_message=lang=='en'?'Unable to get user record!':'\u00a1No fue posible obtener el registro del usuario!';
	else if(msg=='err_user_exists')
		i18n_message=lang=='en'?'User Name already exists!':'\u00a1El Nombre de Usuario ya existe!';
	else if(msg=='err_user_exists_red')
		i18n_message=lang=='en'?'The user does not exist in JetAccess Cloud ERP!'
			:'\u00a1El usuario no se encontr\u00f3 en JetAccess Cloud ERP !';
	else if(msg=='err_username_6characters')
		i18n_message=lang=='en'?'Please enter username with at least 6 characters!':'\u00a1Favor de ingresar un nombre de usuario con al menos 6 caracteres!';

	else if(msg=='msg_config')
		i18n_message=lang=='en'?'Config':'Configurar';
	else if(msg=='msg_controls')
		i18n_message=lang=='en'?'Controls':'Controles';
	else if(msg=='msg_delete')
		i18n_message=lang=='en'?'Delete':'Eliminar';
	else if(msg=='msg_edit')
		i18n_message=lang=='en'?'Edit':'Editar';
	else if(msg=='msg_new')
		i18n_message=lang=='en'?'New':'Nuevo';
	else if(msg=='msg_privilege_saved_successfully')
		i18n_message=lang=='en'?'Privilege saved successfully!':'\u00a1Privilegio guardado exitosamente!';
	else if(msg=='msg_role_title')
		i18n_message=lang=='en'?'Role':'Rol';
	else if(msg=='msg_role_saved_successfully')
		i18n_message=lang=='en'?'Role saved successfully!':'\u00a1Rol guardado exitosamente!';
	else if(msg=='msg_select')
		i18n_message=lang=='en'?'Select...':'Seleccionar...';
	else if(msg=='msg_select_role')
		i18n_message=lang=='en'?'Select a role':'Selecciona un rol';
	else if(msg=='msg_success')
		i18n_message=lang=='en'?'Success!':'\u00a1Correcto!';
	else if(msg=='msg_user_saved_successfully')
		i18n_message=lang=='en'?'User saved successfully!':'\u00a1Usuario guardado exitosamente!';
	else if(msg=='msg_visible')
		i18n_message=lang=='en'?'Visible':'Visible'
	else if(msg=='msg_warning')
		i18n_message=lang=='en'?'Warning!':'\u00a1Advertencia!';
	return i18n_message;
};

//Ingresa los datos en el objeto
function saveObjects(datos, object){
	let index = datos.editdataid;
	object[index] = datos;
};

//Función que se activa cada que presionamos uno de los campos a cambiar
var dataPrivileges = {};
function changePrivileges() {
	var id = event.target.getAttribute("menuid");
	var container = document.getElementById(id);
	var checks = container.querySelectorAll("input");
	var objeto = Array.prototype.reduce.call(checks, function(acumulador, elemento) {
		if (elemento.checked) { // Si el checkbox está marcado
		  acumulador[elemento.getAttribute('id')] = 'on'; // Insertar la llave con valor 'on'
		}
		return acumulador;
	  }, {});
	objeto.editdataid = Number(id);
	objeto.roleid = Number(document.getElementById('roleList').value);
	saveObjects(objeto, dataPrivileges);
};

var changeEvent = new Event('change');
function update(datos){
	$.ajax({
		type: 'POST',
		url: ctx + '/updateprivilege',
		data: $.param(datos),
		async: false,
		success: function(resp){		
		},
		error: function(e){
			$('#putEditPrivilegeError').html(i18n("err_unable_get_privilege") + " (4)");
			$('#EditPrivilegeError').css('display', 'block');
		}
	});
};

// Inserta todos los datos recopilados
function iterar(){
	Object.values(dataPrivileges).forEach(valor => {
		update(valor);
	});
	var selRole = $('#roleList option:selected').val();
			window.location = ctx + "/privileges" + "?language=" + getLanguageURL() + "&srl=" + selRole;
};

// Verifica los permisos existentes
var permisos;

function get(id) {
  var param = "id=" + id + "&role=" + $('#roleList').val();
  $.ajax({
    type: "POST",
    url: ctx + "/editprivilege",
    data: param,
    async: false,
    success: function(data) {
      if (data == '') {
        swal(i18n("msg_error"), i18n("err_unable_get_privilege") + " (5)", "error");
      } else {
        permisos = data[0];
      }
    },
    error: function(data) {
      swal(i18n("msg_error"), i18n("err_unable_get_privilege") + " (6)", "error");
    }
  });
};

//Verifica y muestra los permisos asignados
function verifyChecks(input) {
  let id = input.getAttribute('menuid');
  get(id);
  var ids = permisos.map(function(ids) {
    return ids.privilegesid;
  });
  let boolean = ids.includes(Number(input.value));
  if (boolean) {
    input.checked = true;
  } else {
    input.checked = false;
  }
}

//Obtiene todos los checks
function iterarChecks(clase) {
  let checks = document.querySelectorAll(clase);
  Array.from(checks).forEach(element => {
    verifyChecks(element);
  });
}

function toggleCheckboxes(nodeList) {
  let allChecked = true;
  for (let i = 0; i < nodeList.length; i++) {
    if (!nodeList[i].checked) {
      allChecked = false;
      break;
    }
  }
  for (let i = 0; i < nodeList.length; i++) {
    nodeList[i].checked = !allChecked;

  }
  nodeList.forEach(element => {
    element.dispatchEvent(changeEvent);
  });
}

//Selecciona o deselecciona todos los campos
function allchecks() {
  let id = event.target.getAttribute("menuid");
  let checks = document.getElementById(id).querySelectorAll(".checkPrivileges");
  toggleCheckboxes(checks);
}

// Cambia el texto según el valor del campo
function changeTxt() {
  if (event.target.checked == true) {
    event.target.parentNode.querySelector("div").innerText = "Activo";
    event.target.parentNode.querySelector("div").setAttribute("style", 'user-select: none;color:#5FBEAA;');
  } else if (event.target.checked == false) {
    event.target.parentNode.querySelector("div").innerText = "Inactivo";
    event.target.parentNode.querySelector("div").setAttribute("style", 'user-select: none;color:#8B0000;');
  }
}

// Alterna colores
function colorAll(param, condition) {
  if (condition) {
    param.forEach(element => {
      if (element.checked) {
        element.parentNode.querySelector("div").setAttribute("style", 'user-select: none;color:#5FBEAA;');
      } else {
        element.parentNode.querySelector("div").setAttribute("style", 'user-select: none;color:#8B0000;');
      }
    });
  } else {
    param.forEach(element => {
      element.parentNode.querySelector("div").setAttribute("style", 'user-select: none;color: #212121;');
    });
  }
};

let haveEvents = true;

function setEvents(e) {
  let allChecked = document.querySelectorAll(".checksAll");
  let checks = document.querySelectorAll(".checkPrivileges");
  colorAll(checks, haveEvents);
  
  if (haveEvents) {
    checks.forEach(element => {
      element.disabled = false;
      element.parentNode.setAttribute("style", "cursor:pointer;");
    });
    
    document.getElementById("cancel").setAttribute("style", "all:unset;background-color: #8B0000;margin: 10px; margin-bottom: 20px; outline: none;color:#FFF;padding: 5px; border-radius: 5px; cursor: pointer;");
    haveEvents = false;
    
    allChecked.forEach(element => {
      element.setAttribute("style", "cursor:pointer;");
      element.addEventListener("click", allchecks);
    });
    
    event.target.setAttribute("style", "all:unset;background-color: #212121;margin: 10px; margin-bottom: 20px; outline: none;color:#FFF;padding: 5px; border-radius: 5px; cursor: pointer;");
    event.target.innerHTML = "Guardar";
  } else {
    checks.forEach(element => {
      element.disabled = true;
      element.parentNode.setAttribute("style", "cursor:default;");
    });
    if((document.querySelectorAll(".changed")).length>0){ iterar();}
  
    haveEvents = true;
    allChecked.forEach(element => {
      element.setAttribute("style", "cursor:default;");
      element.removeEventListener("click", allchecks);
    });
    document.getElementById("cancel").setAttribute("style", "all:unset;background-color: #8B0000;display: none; margin: 10px; margin-bottom: 20px; outline: none;color:#FFF;padding: 5px; border-radius: 5px; cursor: pointer;");
    event.target.setAttribute("style", "all:unset;background-color: #5FBEAA;margin: 10px; margin-bottom: 20px; outline: none;color:#FFF;padding: 5px; border-radius: 5px; cursor: pointer;");
    event.target.innerHTML = "Editar";
  }
}
function cancelar(){
	//Esta seccion permite quitar la vizualizacion del boton de cancelar
	event.target.setAttribute("style","all:unset;background-color: #8B0000;display: none; margin: 10px; margin-bottom: 20px; outline: none;color:#FFF;padding: 5px; border-radius: 5px; cursor: pointer;");
	let allChecked=document.querySelectorAll(".checksAll");
	let checks=document.querySelectorAll(".checkPrivileges");
	let changeds=document.querySelectorAll(".changed");
	if(changeds.length!=0){
	iterarChecks(".changed");
	changeds.forEach(element=>{element.dispatchEvent(changeEvent),element.classList.remove("changed")});
	}
	colorAll(checks,false);
	dataPrivileges=[];
	checks.forEach(element=>{element.disabled=true,element.parentNode.setAttribute('style',"cursor:default;")});
	haveEvents=true;
	allChecked.forEach(element=>{element.setAttribute('style',"cursor:default;"), element.removeEventListener("click",allchecks)});
	document.getElementById("cancel").setAttribute("style","all:unset;background-color: #8B0000;display: none; margin: 10px; margin-bottom: 20px; outline: none;color:#FFF;padding: 5px; border-radius: 5px; cursor: pointer;");
	document.getElementById("edit").setAttribute("style","all:unset;background-color: #5FBEAA;margin: 10px; margin-bottom: 20px; outline: none;color:#FFF;padding: 5px; border-radius: 5px; cursor: pointer;");
	document.getElementById("edit").innerHTML="Editar";
	checks.forEach(element=>{element.parentNode.parentNode.setAttribute("style","cursor: default;")});
	event.target.setAttribute("style","all:unset;background-color: #8B0000;display: none; margin: 10px; margin-bottom: 20px; outline: none;color:#FFF;padding: 5px; border-radius: 5px; cursor: pointer;");
};

function isChange(){
	event.target.classList.add("changed");
	event.target.parentNode.parentNode.setAttribute("style","cursor: default; box-shadow: inset 0 0 5px #F66100;");
};

window.addEventListener('load',e=>{iterarChecks(".checkPrivileges");});

// Copiar permisos
var menusVigentes=[];//Array que nos trae todos los permisos vigentes en la db
var permisosRol;//Objeto que en sus values tiene arrays con un objeto por cada permiso
var permisosValidados={};//Array final que se va a iterar para enviar el post
var objectsForPost=[];//array que nos guarda los objetos que ya estan listos para el post

//Esta funcion se encarga de obtener todos los permisos vigentes en el rol en el que estamos
function getRolesPrivileges() {
	var param = "id=" + 0 + "&role=" + $('#roleList').val();
	$.ajax({
	  type: "POST",
	  url: ctx + "/editprivilege",
	  data: param,
	  async: false,
	  success: function(data) {
		if (data == '') {
		  swal(i18n("msg_error"), i18n("err_unable_get_privilege") + " (7)", "error");
		} else {
		  const agrupacionPorRole= data[0].reduce((agrupacion, objeto) => {
			const { menuid } = objeto;
			if (!agrupacion[menuid]) {
			  agrupacion[menuid] = [];
			}
			agrupacion[menuid].push(objeto);
			return agrupacion;
		  }, {});
		  permisosRol=agrupacionPorRole;
		}
	  },
	  error: function(data) {
		swal(i18n("msg_error"), i18n("err_unable_get_privilege") + " (8)", "error");
	  }
	});
  };

  //Esta funcion nos convierte el todos los arrays de permisos que obtenemos en un objeto con el mismo formato con el que se hace push
  function createObjectPermiso(){
	let rolid=event.target.value;
	let arrayRol=Object.values(permisosRol);
	let arrayTemporal=[];
	arrayRol.forEach(elemento=>{
		let objectTemp={};
		let arrayPermisos=[];
		elemento.forEach(element=>{
			arrayPermisos.push(element.privilegesid)});
		objectTemp.editdataid=elemento[0].menuid;
		objectTemp.roleid=Number(rolid);
		arrayPermisos.forEach(permiso=>{objectTemp[`mp_${permiso}`]="on"});
		arrayTemporal.push(objectTemp);
	})
	objectsForPost=arrayTemporal;
  };

//Esta funcion nos trae los permisos que estan vigentes en la base de datos en este momento
function getMenuVigente() {
	var param = "id=" + 0 ;
	$.ajax({
	  type: "POST",
	  url: ctx + "/getDataByMenuId",
	  data: param,
	  async: false,
	  success: function(data) {
		if (data == '') {
		  swal(i18n("msg_error"), i18n("err_unable_get_privilege") + " (9)", "error");
		} else {
			(Object.values(data[0])[0]).forEach(element=>{menusVigentes.push(element.menuid)})
		};
	  },
	  error: function(data) {
		swal(i18n("msg_error"), i18n("err_unable_get_privilege") + " (10)", "error");
	  }
	});
  };

function getValidation(){
	let idrole=event.target.value;
	menusVigentes.forEach(element=>{permisosValidados[element]=null});
	objectsForPost.forEach(permiso=>{permisosValidados[permiso.editdataid]=permiso});
	let arrayValidationNull=Object.keys(permisosValidados);
	arrayValidationNull.forEach(llave=>{if(permisosValidados[llave]==null){
		permisosValidados[llave]={"editdataid":Number(llave),"roleid":Number(idrole)}
	}});
	document.getElementById("sendcopy").addEventListener("click",sendcopy);
	
};

function openModalCopy(){
	let action=event.target.value;
	if(action=="open"){	document.getElementById("modalcopy").setAttribute("style","position: absolute;top: 0;left: 0;z-index: 9999;padding: 25px; background-color: #212121; border-radius: 10px; display:block;");
	}else if(action=='closed'){document.getElementById("modalcopy").setAttribute("style","position: absolute;top: 0;left: 0;z-index: 9999;padding: 25px; background-color: #212121; border-radius: 10px; display:none;");
	permisosValidados={};
	document.getElementById("sendcopy").removeEventListener("click",sendcopy)};
};

function showLoadingModal() {
	document.getElementById('loadingModal').style.display = 'block';};
  function hideLoadingModal() {
	taskCompleted();
	setTimeout(() => {
		document.getElementById('loadingModal').style.display = 'none';
	}, 1500);
	setTimeout(() => {
		var selRole = $('#roleList option:selected').val();
			window.location = ctx + "/privileges" + "?language=" + getLanguageURL() + "&srl=" + selRole;
	}, 1600);
	
  }
  function taskCompleted() {
	// Oculta el elemento que gira
	document.querySelector('#loadingModal > div > div:first-child').style.display = 'none';
	// Muestra el icono de palomita
	document.getElementById('doneIcon').style.display = 'inline-block';
	// Cambia el texto a "Hecho"
	document.getElementById('loadingText').textContent = 'Hecho';
  };
 
  async function sendcopy(){
	showLoadingModal();
	setTimeout(() => {
		Object.values(permisosValidados).forEach(valor => {
		// Llama a la función con cada valor como parámetro
		update(valor)});
	}, 200);
	  setTimeout(() => {
		hideLoadingModal();
	  },5000);
  };

  function nameModal() {
	var id=$("#roleList").val()||0;
	var param = "id=" + id;
	$.ajax({
	  type: "POST",
	  url: ctx + "/getRoleDetails",
	  data: param,
	  async: false,
	  success: function(data) {
		if (data == '') {
		  swal(i18n("msg_error"), i18n("err_unable_get_privilege") + " (11)", "error");
		} else {
			document.getElementById("nameModal").innerHTML="Copiar permisos de "+data[0][0].rolename+" a:"
		};
	  },
	  error: function(data) {
		swal(i18n("msg_error"), i18n("err_unable_get_privilege") + " (12)", "error");
	  }
	});
  };

//window.addEventListener("load",nameModal) 
//window.addEventListener("load",getMenuVigente) 
//window.addEventListener("load",getRolesPrivileges) 