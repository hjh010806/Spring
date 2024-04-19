//// 드롭다운 버튼을 클릭하면 드롭다운 내용을 토글합니다.
//document.querySelector('.nav-link.dropbtn').addEventListener('click', function() {
//  document.querySelector('.dropdown-content').classList.toggle('show');
//});
//
//// 드롭다운 내용이 열려 있는 상태에서 다른 곳을 클릭하면 드롭다운 내용을 닫습니다.
//window.onclick = function(event) {
//  if (!event.target.matches('.dropbtn')) {
//    var dropdowns = document.getElementsByClassName("dropdown-content");
//    var i;
//    for (i = 0; i < dropdowns.length; i++) {
//      var openDropdown = dropdowns[i];
//      if (openDropdown.classList.contains('show')) {
//        openDropdown.classList.remove('show');
//      }
//    }
//  }
//}