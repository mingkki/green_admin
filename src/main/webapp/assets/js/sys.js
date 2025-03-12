// 체크박스의 값을 변경한다.
function set_checkbox(form,fname,val) {
	var chk_count=0;
	for(i=0;i<form.length;i++) {
		if(form[i].type=="checkbox" && form[i].name==fname) {
			if(val=='inv') {
				form[i].checked=!form[i].checked;
			} else {
				form[i].checked=val;
			}
			chk_count++;
		}
	}
	return chk_count;
}

// 체크박스에 하나라도 val값과 일치라면 참
function chk_checkbox(form,fname,val) {
	var Check_List=false;
	for(i=0;i<form.length;i++) {
		if(form[i].type=="checkbox" && form[i].name==fname) {
			if(form[i].checked==val) Check_List=true;
		}
	}
	return Check_List;
}

// POST 전송, 결과값 리턴
function post_delete(href, param) 
{
    if (confirm("한번 삭제한 자료는 복구할 방법이 없습니다.\n\n정말 삭제하시겠습니까?")) 
    { 
        var f = document.createElement('form');
        
        f.setAttribute('method', 'post');
        f.setAttribute('action', href + '?' + param);
        document.body.appendChild(f);
        f.submit();
	}
}

function post_url(href, param, msg) 
{
    if (confirm(msg)) 
    { 
        var f = document.createElement('form');
        
        f.setAttribute('method', 'post');
        f.setAttribute('action', href + '?' + param);
        document.body.appendChild(f);
        f.submit();
	}
}

function setCookie(name,value,expiredays) 
{ 
	var todayDate = new Date(); 
	todayDate.setDate( todayDate.getDate() + expiredays ); 
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";" 
}
/*쿠키추출*/
function getCookie( name )
{
	var nameOfCookie = name + "=";
	var x = 0;
	while ( x <= document.cookie.length )
	{
		var y = (x+nameOfCookie.length);
		if ( document.cookie.substring( x, y ) == nameOfCookie ) 
		{
			if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 ) endOfCookie = document.cookie.length;
			return unescape( document.cookie.substring( y, endOfCookie ) );
		}
		x = document.cookie.indexOf( " ", x ) + 1;
		if ( x == 0 ) break;
	}
	return "";
}