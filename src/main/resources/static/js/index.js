const imgContainerElement = document.querySelector('.image-container');

const btnEl = document.querySelector('.btn');
const  imageNum = 4;

const images = document.querySelectorAll('.photo');

// Add click event listener to each image
images.forEach(image => {
    image.addEventListener('click', function() {
        // Get the source of the clicked image
        const src = image.src;

        // Encode the source URL to ensure it's properly passed as a parameter
        const encodedSrc = src;// encodeURIComponent(src);

        // Construct the URL for the next page with the source parameter
        const nextPageUrl = `puzzle?image=${encodedSrc}`;

        // Navigate to the next page
        window.location.href = nextPageUrl;
    });
});

btnEl.addEventListener("click",() =>{
    addNewImages();
});

function addNewImages(){
    for (let index = 0; index < imageNum; index++) {
        const newImgEl = document.createElement("img");
        newImgEl.src = `https://picsum.photos/300?random=${Math.floor(Math.random() * 2000)}`;
        newImgEl.className= "photo";
        imgContainerElement.appendChild(newImgEl);
    }
}

