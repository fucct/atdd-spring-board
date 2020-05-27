import { EVENT_TYPE } from '../../utils/constants.js'
import api from '../../api/index.js'

function Join() {
  const $userId = document.querySelector('#userId');
  const $name = document.querySelector('#name');
  const $password = document.querySelector('#password');
  const $passwordCheck = document.querySelector('#password-check');
  const $joinButton = document.querySelector('#join-button');

  const onClickJoinButton = event => {
    event.preventDefault();

    if ($password.value !== $passwordCheck.value) {
      alert("비밀번호가 일치하지 않습니다! 😭");
      return;
    }

    const joinForm = {
      userId: $userId.value,
      name: $name.value,
      password: $password.value,
    };

    api.member.join(joinForm)
    .then(response => {
      if (!response.ok) {
        throw response;
      }
      window.location = "/login";
    }).catch(response => response.json())
    .then(errorResponse => {
      if (errorResponse) {
        alert(errorResponse.message);
      }
    });
  }

  const initEventListener = () => {
    $joinButton.addEventListener(EVENT_TYPE.CLICK, onClickJoinButton)
  }

  this.init = () => {
    initEventListener();
  }
}

const join = new Join();
join.init();
