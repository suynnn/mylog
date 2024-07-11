document.addEventListener("DOMContentLoaded", function () {
    const postListElement = document.getElementById('post-list');

    const handleScroll = () => {
        const { scrollTop, scrollHeight, clientHeight } = document.documentElement;

    };

    postListElement.addEventListener('click', function (event) {
        const card = event.target.closest('li');
        if (card) {
            const postId = card.getAttribute('data-id');
            const username = card.getAttribute('data-username');
            if (postId && username) {
                window.location.href = `/posts/@${username}/${postId}`;
            }
        }
    });

    window.addEventListener('scroll', handleScroll);
});
