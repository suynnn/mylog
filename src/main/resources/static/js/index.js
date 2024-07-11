document.addEventListener("DOMContentLoaded", function () {
    const postListElement = document.getElementById('post-list');
    let page = 1;
    const pageSize = 10;

    const loadPosts = () => {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', `/posts?page=${page}&size=${pageSize}`, true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                const posts = JSON.parse(xhr.responseText);
                posts.forEach(post => {
                    const li = document.createElement('li');
                    li.classList.add('col-md-4', 'mb-4');
                    li.setAttribute('data-id', post.id);

                    li.innerHTML = `
                        <div class="card post-card" onclick="location.href='/posts/${post.id}';">
                            <img src="${post.thumbnailUrl}" class="card-img-top" alt="Thumbnail">
                            <div class="card-body post-card-content">
                                <h5 class="card-title">${post.title}</h5>
                                <p class="card-text">${post.content.substring(0, 150)}...</p>
                                <small class="text-muted">${post.createdAt}</small>
                                <small class="text-muted">댓글 ${post.comments.length}개</small>
                            </div>
                            <div class="card-footer post-card-footer">
                                <small>${post.user.username}</small>
                                <small>좋아요 ${post.likes.length}</small>
                            </div>
                        </div>
                    `;
                    postListElement.appendChild(li);
                });
                page++;
            }
        };
        xhr.send();
    };

    const handleScroll = () => {
        const { scrollTop, scrollHeight, clientHeight } = document.documentElement;
        if (scrollTop + clientHeight >= scrollHeight - 5) {
            loadPosts();
        }
    };

    window.addEventListener('scroll', handleScroll);
});
