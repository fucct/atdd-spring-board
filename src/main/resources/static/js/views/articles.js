import { EVENT_TYPE, ERROR_MESSAGE, KEY_TYPE } from "../../utils/constants.js";
import { listItemTemplate } from "../../utils/templates.js";

function Articles() {
  const $articleInput = document.querySelector("#article-input");
  const $articleList = document.querySelector("#article-list");
  const $articleAddBtn = document.querySelector("#article-add-btn")
  const onAddStationHandler = event => {
    if (event.key !== KEY_TYPE.ENTER && event.type !== EVENT_TYPE.CLICK ) {
      return;
    }

    event.preventDefault();
    const $titleInput = document.querySelector("#title");
    const title = $titleInput.value;
    const $userNameInput = document.querySelector("#user-name");
    const userName = $userNameInput.value;
    const $contentInput = document.querySelector("#content");
    const content = $contentInput.value;
    if (!title || !userName || !content) {
      alert(ERROR_MESSAGE.NOT_EMPTY);
      return;
    }
    let article = {
      title: title,
      userName: userName,
      content: content
    };

    fetch('/articles', {
      method:'POST',
      headers:{
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(article)
    }).then(response => response.headers)
    .then(jsonResponse=>{
      const uri = jsonResponse.get("Location");
    });
  };

  const onRemoveStationHandler = event => {
    const $target = event.target;
    const isDeleteButton = $target.classList.contains("mdi-delete");
    if (isDeleteButton) {
      $target.closest(".list-item").remove();
    }
  };

  const initEventListeners = () => {
    $articleInput.addEventListener(EVENT_TYPE.KEY_PRESS, onAddStationHandler);
    $articleAddBtn.addEventListener(EVENT_TYPE.CLICK, onAddStationHandler);
  };

  const init = () => {
    initEventListeners();
  };

  return {
    init
  };
}

function getLines(){
  fetch('/articles', {
    method: 'GET'
  }.then(response=>response.json()))
  .then(jsonResponse)

}

const adminStation = new Articles();
adminStation.init();
