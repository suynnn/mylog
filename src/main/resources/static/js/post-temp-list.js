document.addEventListener("DOMContentLoaded", function () {
    const postListElement = document.getElementById('post-list');

    const handleScroll = () => {
        const { scrollTop, scrollHeight, clientHeight } = document.documentElement;
        // 스크롤 이벤트 처리 로직 추가
    };

    postListElement.addEventListener('click', function (event) {
        const card = event.target.closest('li');
        if (card) {
            const postId = card.getAttribute('data-id');
            const username = card.getAttribute('data-username');
            if (postId && username) {
                window.location.href = `/posts/update/${postId}`;
            }
        }
    });

    window.addEventListener('scroll', handleScroll);

    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
        link.addEventListener('click', function (event) {
            event.preventDefault();
            navLinks.forEach(link => link.classList.remove('active'));
            this.classList.add('active');
            console.log(`${this.textContent} 순으로 정렬`);
            // 해당 순서로 포스트를 정렬하는 로직 추가
        });
    });
});
