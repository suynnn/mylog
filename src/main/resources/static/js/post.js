document.addEventListener('DOMContentLoaded', function () {
    const submitCommentButton = document.getElementById('submitComment');
    const commentContent = document.getElementById('commentContent');
    const commentList = document.querySelector('.comment-list');
    const prevPageButton = document.getElementById('prevPage');
    const nextPageButton = document.getElementById('nextPage');

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
        comments.forEach(commentDto => {
            const commentItem = document.createElement('div');
            commentItem.classList.add('comment-item');

            const commentInfo = document.createElement('div');
            commentInfo.classList.add('comment-info');
            commentInfo.textContent = `${commentDto.nickname} • ${new Date(commentDto.createdAt).toLocaleString()}`;

            const commentContent = document.createElement('div');
            commentContent.classList.add('comment-content');
            commentContent.textContent = commentDto.content;

            commentItem.appendChild(commentInfo);
            commentItem.appendChild(commentContent);
            commentList.appendChild(commentItem);
        });
    }

    function updatePagination(pageData) {
        prevPageButton.parentElement.classList.toggle('disabled', pageData.first);
        nextPageButton.parentElement.classList.toggle('disabled', pageData.last);
    }

    submitCommentButton.addEventListener('click', function () {
        const content = commentContent.value.trim();
        if (content === '') {
            alert('댓글 내용을 입력하세요.');
            return;
        }

        const postId = document.querySelector('input[name="postId"]').value;
        const userId = document.querySelector('input[name="commentUserId"]').value;

        const commentData = {
            content: content,
            commentClass: 0, // 댓글 클래스 0으로 설정
            parentId: null, // 부모 댓글 ID는 null
            userId: userId, // 현재 로그인된 사용자 ID
            postId: postId // 현재 게시글 ID
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
                    const commentDto = data.data;
                    fetchComments(currentPage); // 새 댓글 등록 후 페이지 갱신
                    commentContent.value = ''; // 입력 필드 비우기
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

    fetchComments(currentPage); // 페이지 로드 시 첫 댓글 페이지 가져오기
});
