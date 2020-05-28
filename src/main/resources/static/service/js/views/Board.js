function Favorite() {
  const $favoriteList = document.querySelector("#favorite-list");

  this.init = () => {
    alert("하이");
  };
}

const favorite = new Favorite();
favorite.init();
