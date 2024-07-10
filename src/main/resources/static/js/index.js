const imgContainerElement = document.querySelector('.image-container');

const  imageNum = 4;

const images = document.querySelectorAll('.photo');

itemClicked=function(element) {
    const id = element.getElementsByTagName("img")[0].id;
    const nextPageUrl = `puzzle?id=${id}`;
    // Navigate to the next page
    window.location.href = nextPageUrl;
}


