import { ERROR_MESSAGE, EVENT_TYPE } from '../../utils/constants.js'
import api from '../../api/index.js';
import { setCookie } from '../../utils/loginUtils.js';

function Login() {
  const $loginButton = document.querySelector('#login-button');
  const $userId = document.querySelector('#user-id');
  const $password = document.querySelector('#password');

  const onLogin = event => {
    event.preventDefault();
    const userIdValue = $userId.value;
    const passwordValue = $password.value;
    const data = {
      userId: userIdValue,
      password: passwordValue,
    };
    if (!userIdValue || !passwordValue) {
      Snackbar.show({
        text: ERROR_MESSAGE.LOGIN_FAIL,
        pos: 'bottom-center',
        showAction: false,
        duration: 2000
      });
      return;
    }
    api.member.login(data)
    .then(response => {
      if (!response.ok) {
        throw response;
      }
      return response.json();
    }).then(tokenResponse => {
      setCookie(tokenResponse.accessToken);
      window.location = "/";
    }).catch(error => error.json())
    .then(errorResponse => {
      if (errorResponse) {
        alert(errorResponse.message);
      }
    });
  }

  this.init = () => {
    $loginButton.addEventListener(EVENT_TYPE.CLICK, onLogin);
  }
}

const login = new Login();
login.init();
