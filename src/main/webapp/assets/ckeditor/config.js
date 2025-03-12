/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.height = '300px';
	config.toolbar = [['FontSize'],['Bold','Italic','Underline','Strike','Subscript','Superscript','TextColor','BGColor','Blockquote','RemoveFormat','NumberedList','BulletedList'],'/',['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],['Link','Unlink','Find','Replace','SelectAll','-','Image','Table','Smiley','SpecialChar'],'/',['Source','Preview','Templates','Print'],['Cut','Copy','Paste','PasteText','PasteFromWord','Undo','Redo','Maximize','Iframe']];
	config.enterMode = CKEDITOR.ENTER_BR;
    config.shiftEnterMode = CKEDITOR.ENTER_P;
	config.fillEmptyBlocks = false;
	config.htmlEncodeOutput = false;
	config.entities = false;
	config.autoParagraph = false;
	config.allowedContent = true;
	config.filebrowserUploadUrl = '/ckupload';
};

CKEDITOR.on('dialogDefinition', function (ev) {
	var dialogName = ev.data.name;
	var dialog = ev.data.definition.dialog;
	var dialogDefinition = ev.data.definition;
		if (dialogName == 'image') {
			dialog.on('show', function (obj) {
			this.selectPage('Upload'); //업로드텝으로 시작
		});
		dialogDefinition.removeContents('advanced'); // 자세히탭 제거
		dialogDefinition.removeContents('Link'); // 링크탭 제거
	}
});
