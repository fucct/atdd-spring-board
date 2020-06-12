import api from '../../api/index.js';
import { EVENT_TYPE } from "../../utils/constants.js"
import { deleteCookie, getCookie } from '../../utils/loginUtils.js';

function MyPage() {
  const $logoutButton = document.querySelector("#logout-button");

  const logout = (e) => {
    e.preventDefault();
    if (!getCookie()) {
      alert("이미 로그아웃 되었습니다.");
    }
    deleteCookie();
    alert("정상적으로 로그아웃 되었습니다.");
    window.location = "/login";
  }

  this.init = () => {
    const $id = document.querySelector("#id");
    const $userId = document.querySelector("#userId");
    const $name = document.querySelector("#name");

    if (getCookie()) {
      api.member.myPage().then(response => {
        $id.dataset.id = response.id;
        $userId.innerText = response.userId;
        $name.innerText = response.name;
      });
    } else {
      alert("로그인을 해주세요");
      window.location = "/login";
    }
    $logoutButton.addEventListener(EVENT_TYPE.CLICK, logout);
  }
}

const myPage = new MyPage();
myPage.init();
