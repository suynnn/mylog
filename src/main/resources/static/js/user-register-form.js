document.addEventListener('DOMContentLoaded', function() {
    let isUsernameValid = false;
    let isEmailValid = false;

    function updateRegisterButtonState() {
        document.getElementById('register-button').disabled = !(isUsernameValid && isEmailValid);
    }

    function checkPasswordMatch() {
        const passwordInput = document.getElementById('password');
        const passwordCheckInput = document.getElementById('password-check');
        const passwordFeedback = document.getElementById('password-feedback');

        if (passwordInput.value !== passwordCheckInput.value) {
            passwordFeedback.textContent = '비밀번호가 일치하지 않습니다.';
        } else {
            passwordFeedback.textContent = '';
        }
    }

    document.getElementById('check-username').addEventListener('click', function() {
        const usernameInput = document.getElementById('username');
        const username = usernameInput.value.trim();
        const usernameFeedback = document.getElementById('username-feedback');
        if (username === '') {
            usernameFeedback.textContent = '아이디를 입력하세요.';
            usernameFeedback.style.color = 'red';
            return;
        }

        fetch(`/api/users/exists/username/${username}`)
            .then(response => response.json())
            .then(data => {
                if (data.data) {
                    usernameInput.value = '';
                    usernameFeedback.textContent = '이미 존재하는 아이디입니다.';
                    usernameFeedback.style.color = 'red';
                } else {
                    usernameInput.readOnly = true;
                    document.getElementById('check-username').disabled = true;
                    document.getElementById('reset-username').classList.remove('d-none');
                    usernameFeedback.textContent = '사용 가능한 아이디입니다.';
                    usernameFeedback.style.color = 'green';
                    isUsernameValid = true;
                    updateRegisterButtonState();
                }
            });
    });

    document.getElementById('reset-username').addEventListener('click', function() {
        const usernameInput = document.getElementById('username');
        usernameInput.value = '';
        usernameInput.readOnly = false;
        document.getElementById('check-username').disabled = false;
        document.getElementById('reset-username').classList.add('d-none');
        document.getElementById('username-feedback').textContent = '';
        isUsernameValid = false;
        updateRegisterButtonState();
    });

    document.getElementById('check-email').addEventListener('click', function() {
        const emailInput = document.getElementById('email');
        const email = emailInput.value.trim();
        const emailFeedback = document.getElementById('email-feedback');
        if (email === '') {
            emailFeedback.textContent = '이메일을 입력하세요.';
            emailFeedback.style.color = 'red';
            return;
        }

        fetch(`/api/users/exists/email/${email}`)
            .then(response => response.json())
            .then(data => {
                if (data.data) {
                    emailInput.value = '';
                    emailFeedback.textContent = '이미 존재하는 이메일입니다.';
                    emailFeedback.style.color = 'red';
                } else {
                    emailInput.readOnly = true;
                    document.getElementById('check-email').disabled = true;
                    document.getElementById('reset-email').classList.remove('d-none');
                    emailFeedback.textContent = '사용 가능한 이메일입니다.';
                    emailFeedback.style.color = 'green';
                    isEmailValid = true;
                    updateRegisterButtonState();
                }
            });
    });

    document.getElementById('reset-email').addEventListener('click', function() {
        const emailInput = document.getElementById('email');
        emailInput.value = '';
        emailInput.readOnly = false;
        document.getElementById('check-email').disabled = false;
        document.getElementById('reset-email').classList.add('d-none');
        document.getElementById('email-feedback').textContent = '';
        isEmailValid = false;
        updateRegisterButtonState();
    });

    document.getElementById('password').addEventListener('input', checkPasswordMatch);
    document.getElementById('password-check').addEventListener('input', checkPasswordMatch);
});
