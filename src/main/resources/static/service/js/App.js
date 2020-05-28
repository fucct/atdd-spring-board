import { initLoginNavigation, initNavigation } from '../utils/templates.js'
import api from '../api/index.js';

function SubwayApp() {
  this.init = () => {
    if (getCookie()) {
      api.member.myPage().then(response => {
        console.log(response);
        initLoginNavigation(response.name);
      })
    } else {
      initNavigation();
    }
  }

  const getCookie = function () {
    const value = document.cookie.match('(^|;) ?token=([^;]*)(;|$)');
    return value ? value[2] : null;
  };
}

const subwayApp = new SubwayApp()
subwayApp.init()
