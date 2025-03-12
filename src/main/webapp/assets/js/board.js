function board_manager()
{
	if (!chk_checkbox(boardListForm, 'chks', true)){
		alert('게시글을 선택해 주세요');
		return false;
	}

	window.open('', "board_manage", 'scrollbars=yes,width=600,height=350');
	document.boardListForm.act.value='manage';
	document.boardListForm.target='board_manage';
	document.boardListForm.submit();
	return false;
}

function commentBox(comment_id, work)
{
	document.getElementById('commentWriteForm').style.display = 'none';

	var el_id = 'commentWriteForm';

	if (comment_id)
	{
		if (work == 'reply')
		{
			el_id = 'reply_' + comment_id;
		}
		else
		{
			el_id = 'modify_' + comment_id;
		}
	}

    if (save_before != el_id)
    {
        if (save_before)
        {
            document.getElementById(save_before).style.display = 'none';
            document.getElementById(save_before).innerHTML = '';
        }

        document.getElementById(el_id).style.display = '';
        document.getElementById(el_id).innerHTML = save_html;

		if (work == 'modify')
		{
			$('#' + el_id).find('textarea[name="comment"]').val($('#comment_' + comment_id).text());
		}

		$('#' + el_id).find('input[name="commentId"]').val(comment_id);
		$('#' + el_id).find('input[name="mode"]').val(work);

        save_before = el_id;

    }
}