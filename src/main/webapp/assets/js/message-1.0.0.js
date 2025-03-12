var g_MESSAGE = {	
		'KR': {
			'MSG-REQUIRED': '{0}(은)는 필수항목입니다.',
			'MSG-MINLEN': '{0}(은)는 {1}자 이상 입력하셔야 합니다.',
			'MSG-MAXLEN': '{0}는 {1}자 미만 입력하셔야 합니다.',
			'MSG-TYPEERROR': '{0}는 {1}타입에 맞지않습니다.',
			'MSG-TYPENUMBERERROR':  '{0}는 숫자타입에 맞지않습니다.',
			'MSG-TYPEMAILERROR':  '{0}는 메일타입에 맞지않습니다.',
			'MSG-TYPEZIPCODEERROR':  '{0}는 우편번호타입에 맞지않습니다.',
			'MSG-ATTCHE-REQUIRED': '{0}을(를) 첨부하셔야 합니다.',
			'MSG-TYPEDATEERROR': '{0}는 날짜형식에 맞지않습니다.',
			'MSG-TYPETIMEERROR': '{0}는 시간형식에 맞지않습니다.',
			'MSG-TYPETELERROR': '{0}는 전화번호형식에 맞지않습니다.',
			'MSG-TYPEIDERROR': '{0} 5~15자이내 영문,숫자만 가능합니다.',
			'MSG-TYPEPWERROR8': '{0} 8~15자이내 영문,숫자,특수문자가 포함되어 있어야 합니다.',
			'MSG-TYPEPWERROR6': '{0} 6~15자이내 문자,숫자,특수문자가 포함되어 있어야 합니다.',
			'MSG-TYPEPWERROR': '{0} 6~12자이내 문자와 숫자가 포함되어 있어야 합니다.',
			'MSG-NOTEQUAL': '{0}이 일치하지 않습니다.',
			'MSG-NOTCHECK': '{0}를 하셔야합니다.',			
			'MSG-POSTFAIL': '{0}을(를) 실패했습니다.',
			'MSG-POSTSUCCESS': '{0}이 정상적으로 처리되었습니다.',	
			
			'MSG_NOREGID_FAIL': '등록 ID가 없습니다.',
			'MSG_INSERTCHK_FAIL': '이미 등록되어 있습니다. 중복 등록은 할 수 없습니다.',
						
			'MSG_BBS_INSERT': '정상적으로 저장되었습니다.',	
			'MSG_BBS_DELETE': '정상적으로 삭제되었습니다.',	
			'MSG_BBS_DELETE_SUSS': '정상적으로 삭제되었습니다.',
			'MSG_BBS_DELETE_FAIL': '삭제을 실패했습니다.',	
			'MSG_VALID_XSS': '사용할 수 없는 문자가 들어 있습니다. 문자를 수정 후 다시 시도하세요.',
			
			'UPLOAD_VALID_SIZE': '업로드 용량이 초가되었습니다.',
			'UPLOAD_VALID_TYPE': '업로드 가능한 타입이 아닙니다.',
			
			
			'MSG_INSERT_FAIL': '저장을 실패했습니다.',
			'MSG_DELETE_FAIL': '삭제를 실패했습니다.',
			'MSG_CART_CKINSERT_FAIL': '이미 등록되어 있습니다. ',
			'MSG_CKLOGIN_FAIL': '로그인을 하셔야 사용가능합니다. ',
			
			
			'MSG_CONFIRM_DELETE': '정말로 삭제하시겠습니까?',
			
			'FAIL.LOGIN.STATENO': '회원님은 사용정지 상태입니다.',
			'FAIL.LOGIN.PW': '아이디 또는 비밀번호를 다시 확인하세요.',
			'FAIL.LOGIN.NOMEMBER': '아이디 또는 비밀번호를 다시 확인하세요.'
		},
	 	'US': {
			'MSG001': 'The name is required.'   	 
	 	},
	 	showMessage: function(lang, code, params)
	 	{
	 		var Message = g_MESSAGE;
			 var msg = '';
			
			 if( lang == 'ko-KR')
			 {
				lang = 'KR';
			 }
	 		try{
	 			msg = Message[lang][code]; 	
	 			if( params != undefined && params != null)
	 			{
	 				for(var i = 0; i < params.length; i++)
	 				{
	 					msg = msg.replace('{' + i + '}', params[i]);
	 				}
	 			}
	 		}catch(e){
	 			msg = '에러코드 찾기 실패';
	 		}
	 		return msg;
	 	}
};
