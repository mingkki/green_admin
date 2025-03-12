$.validator.setDefaults(
{
	onkeyup:false,
	onclick:false,
    onfocusout:false,
    showErrors:function(errorMap, errorList)
    {    	
       	if (this.numberOfInvalids()) 
    	{    	
			var caption = $(errorList[0].element).attr('title');
			var label = caption || $('body').find('label[for="'+$(errorList[0].element).attr('id')+'"]:first').text();
			
    		alert('['+label+'] ' + errorList[0].message);
            $(errorList[0].element).focus();
    	}
    }
});