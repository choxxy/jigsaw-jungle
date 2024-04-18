const imgContainerElement = document.querySelector('.image-container');

const btnEl = document.querySelector('.btn');

btnEl.addEventListener("click",() =>{
    imageNum = 4;
    addNewImages();
});

function addNewImages(){
    for (let index = 0; index < imageNum; index++) {
        const newImgEl = document.createElement("img");
        newImgEl.src = `https://picsum.photos/300?random=${Math.floor(Math.random() * 2000)}`;
        imgContainerElement.appendChild(newImgEl);
    }
}

