document.addEventListener('DOMContentLoaded', function() {
    const passwordBtn = document.getElementById('passwordBtn');
    const passwordModal = new bootstrap.Modal(document.getElementById('passwordModal'));
    const savePasswordBtn = document.getElementById('savePasswordBtn');
    const passwordMatchError = document.getElementById('passwordMatchError');
    const passwordRuleError = document.getElementById('passwordRuleError');
    const newPasswordInput = document.getElementById('newPassword');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const withdrawBtn = document.getElementById('withdrawBtn');

    passwordBtn.addEventListener('click', function() {
        passwordMatchError.classList.add('d-none');
        passwordRuleError.classList.add('d-none');
        newPasswordInput.value = '';
        confirmPasswordInput.value = '';
        passwordModal.show();
    });

    savePasswordBtn.addEventListener('click', function() {
        const newPassword = newPasswordInput.value;
        const confirmPassword = confirmPasswordInput.value;

        const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@#$%^&+=!])[A-Za-z\d@#$%^&+=!]{4,30}$/;

        passwordMatchError.classList.add('d-none');
        passwordRuleError.classList.add('d-none');

        if (newPassword !== confirmPassword) {
            passwordMatchError.classList.remove('d-none');
        } else if (!passwordRegex.test(newPassword)) {
            passwordRuleError.classList.remove('d-none');
        } else {
            document.querySelector('input[name="password"]').value = newPassword;
            passwordModal.hide();
            passwordBtn.classList.add('disabled');
            passwordBtn.textContent = '비밀번호 변경 완료';
            passwordBtn.setAttribute('disabled', true);
        }
    });

    withdrawBtn.addEventListener('click', function() {
        window.location.href = '/users/delete';
    });
});
