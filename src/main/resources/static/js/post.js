document.addEventListener('DOMContentLoaded', function () {
    const submitCommentButton = document.getElementById('submitComment');
    const commentContent = document.getElementById('commentContent');
    const commentList = document.querySelector('.comment-list');
    const prevPageButton = document.getElementById('prevPage');
    const nextPageButton = document.getElementById('nextPage');
    const currentUserId = document.getElementById('currentUserId') != null ? document.getElementById('currentUserId').value : null; // 현재 로그인한 사용자 ID

    let currentPage = 0;
    const pageSize = 20;

    function fetchComments(page) {
        const postId = document.querySelector('input[name="postId"]').value;

        fetch(`http://localhost:8080/api/comments/list?postId=${postId}&page=${page}&size=${pageSize}`)
            .then(response => response.json())
            .then(data => {
                if (data.responseStatus === 'OK') {
                    renderComments(data.data.content);
                    updatePagination(data.data);
                } else {
                    alert('댓글 목록을 불러오는데 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('댓글 목록을 불러오는 중 오류가 발생했습니다.');
            });
    }

    function renderComments(comments) {
        commentList.innerHTML = '';

        const commentMap = {};

        comments.forEach(commentDto => {
            commentMap[commentDto.id] = commentDto;
            commentDto.replies = [];
        });

        comments.forEach(commentDto => {
            if (commentDto.parentId !== null) {
                commentMap[commentDto.parentId].replies.push(commentDto);
            }
        });

        comments.forEach(commentDto => {
            if (commentDto.parentId === null) {
                renderCommentItem(commentDto, commentList, 0);
            }
        });
    }

    function renderCommentItem(commentDto, commentContainer, depth) {
        const commentItem = document.createElement('div');
        commentItem.classList.add('comment-item');
        commentItem.dataset.commentId = commentDto.id;
        commentItem.dataset.commentClass = commentDto.commentClass;

        const commentInfo = document.createElement('div');
        commentInfo.classList.add('comment-info');

        const profileImage = document.createElement('img');
        profileImage.src = `${commentDto.profile}`;
        profileImage.alt = 'Profile Image';
        profileImage.classList.add('profile-image');
        commentInfo.appendChild(profileImage);

        const infoText = document.createElement('span');
        infoText.textContent = `${commentDto.nickname} • ${new Date(commentDto.createdAt).toLocaleString()}`;
        commentInfo.appendChild(infoText);

        const commentContent = document.createElement('div');
        commentContent.classList.add('comment-content');
        commentContent.textContent = commentDto.content;

        const toggleRepliesButton = document.createElement('button');
        toggleRepliesButton.classList.add('btn', 'btn-link', 'btn-sm');
        toggleRepliesButton.textContent = '답글보기';
        toggleRepliesButton.addEventListener('click', function () {
            toggleRepliesVisibility(commentItem, toggleRepliesButton);
        });

        const deleteButton = document.createElement('button');
        deleteButton.classList.add('btn', 'btn-danger', 'btn-sm');
        deleteButton.textContent = '삭제';
        deleteButton.addEventListener('click', function () {
            if (currentUserId == commentDto.userId) {
                deleteComment(commentDto.id);
            } else {
                alert('자신이 작성한 댓글만 삭제할 수 있습니다.');
            }
        });

        const replyForm = document.createElement('div');
        replyForm.classList.add('reply-form', 'd-none');
        replyForm.innerHTML = `
            <textarea class="form-control mb-2" rows="2" placeholder="답글을 작성하세요..."></textarea>
            <button class="btn btn-primary btn-sm">답글 작성</button>
        `;
        replyForm.querySelector('button').addEventListener('click', function () {
            if (currentUserId) {
                submitReply(commentDto.id, commentDto.commentClass + 1, replyForm.querySelector('textarea').value, commentItem);
            } else {
                alert('로그인한 사용자만 답글을 작성할 수 있습니다.');
            }
        });

        commentItem.appendChild(commentInfo);
        commentItem.appendChild(commentContent);
        commentItem.appendChild(toggleRepliesButton);
        commentItem.appendChild(deleteButton);
        commentItem.appendChild(replyForm);

        const repliesContainer = document.createElement('div');
        repliesContainer.classList.add('replies', `depth-${depth}`, 'd-none');
        commentItem.appendChild(repliesContainer);

        commentContainer.appendChild(commentItem);

        commentDto.replies.forEach(replyDto => {
            renderCommentItem(replyDto, repliesContainer, depth + 1);
        });
    }

    function toggleRepliesVisibility(commentItem, toggleButton) {
        const repliesContainer = commentItem.querySelector('.replies');
        const replyForm = commentItem.querySelector('.reply-form');
        const isHidden = repliesContainer.classList.toggle('d-none');
        replyForm.classList.toggle('d-none', isHidden);
        toggleButton.textContent = isHidden ? '답글보기' : '답글숨기기';
    }

    function updatePagination(pageData) {
        prevPageButton.parentElement.classList.toggle('disabled', pageData.first);
        nextPageButton.parentElement.classList.toggle('disabled', pageData.last);
    }

    function submitReply(parentId, commentClass, content, commentItem) {
        if (!content.trim()) {
            alert('답글 내용을 입력하세요.');
            return;
        }

        const postId = document.querySelector('input[name="postId"]').value;
        const userId = document.querySelector('input[name="commentUserId"]').value;

        const replyData = {
            content: content,
            commentClass: commentClass,
            parentId: parentId,
            userId: userId,
            postId: postId
        };

        fetch('http://localhost:8080/api/comments/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(replyData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.responseStatus === 'OK') {
                    fetchComments(currentPage);
                } else {
                    alert('답글 작성에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('답글 작성 중 오류가 발생했습니다.');
            });
    }

    function deleteComment(commentId) {
        fetch(`http://localhost:8080/api/comments/delete/${commentId}`, {
            method: 'DELETE',
        })
            .then(response => response.json())
            .then(data => {
                if (data.responseStatus === 'OK') {
                    fetchComments(currentPage);
                } else {
                    alert('댓글 삭제에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('댓글 삭제 중 오류가 발생했습니다.');
            });
    }

    submitCommentButton.addEventListener('click', function () {
        if (!currentUserId) {
            alert('로그인한 사용자만 댓글을 작성할 수 있습니다.');
            return;
        }

        const content = commentContent.value.trim();
        if (content === '') {
            alert('댓글 내용을 입력하세요.');
            return;
        }

        const postId = document.querySelector('input[name="postId"]').value;
        const userId = document.querySelector('input[name="commentUserId"]').value;

        const commentData = {
            content: content,
            commentClass: 0,
            parentId: null,
            userId: userId,
            postId: postId
        };

        fetch('http://localhost:8080/api/comments/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(commentData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.responseStatus === 'OK') {
                    fetchComments(currentPage);
                    commentContent.value = '';
                } else {
                    alert('댓글 작성에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('댓글 작성 중 오류가 발생했습니다.');
            });
    });

    prevPageButton.addEventListener('click', function (event) {
        event.preventDefault();
        if (currentPage > 0) {
            currentPage--;
            fetchComments(currentPage);
        }
    });

    nextPageButton.addEventListener('click', function (event) {
        event.preventDefault();
        currentPage++;
        fetchComments(currentPage);
    });

    fetchComments(currentPage);
});
