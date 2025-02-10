;$("#test").on('click', function(){
	$('#datatable-buttons').html('');
	$.ajax({
		type:'POST',
		url:ctx + '/testing',
		data:'mytext='+$('#mytext').val(),
		async:false,
		success:function(data){
			data=data[0];
			var res='';
			for(var d=0;d<Object.keys(data).length;d++){
				res+='<tr>';
				if(d==0){
					res+='<th class="tac" contenteditable="true">Reg.</th>';
					for (h=0;h<Object.keys(data[d]).length;h++){
						if(typeof(data[0])=='object'){
							res+='<th contenteditable="true">'+Object.keys(data[d])[h]+'</th>';
						}else{
							res+='<th contenteditable="true">0</th>';
							break;
						}
					}
					res+='</tr>';
				}
				res+='<td class="tac">'+d+'</td>';
				for (r=0;r<Object.keys(data[d]).length;r++){
					if(typeof(data[0])=='object'){
						res+='<td>'+Object.values(data[d])[r]+'</td>';
					}else{
						res+='<td>'+data[d]+'</td>';
						break
					}
				}
				res+='</tr>';
			}
			$('#datatable-buttons').append(res);
		},error:function(er) {
			$('#datatable-buttons').append('<tr><td>Message</td><td>Status</td></tr>'
				+ '<tr><td>Error</td><td>Communication fail</td></tr>');
		}
	});
});

$('#clear').on('click', function(){
	$('#datatable-buttons').html('');
});