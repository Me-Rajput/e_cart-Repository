import React from 'react'

export default function Carousel() {
  return (
    <div style={{margin:"15px"}}>
      <div id="carouselExample" class="carousel slide" data-bs-ride="carousel">
  <div class="carousel-inner" >
    <div class="carousel-item active active" data-bs-interval="5000">
      <img  src="https://images-eu.ssl-images-amazon.com/images/G/31/img23/Fashion/GW/Sep/OneCard-2000/Unrec-PC-3000._CB596309095_.jpg" class="d-block w-100" alt="..." style={{height:"300px"}} />
    </div>
    <div class="carousel-item" data-bs-interval="5000">
      <img src="https://images-eu.ssl-images-amazon.com/images/G/31/img22/Baby/cnnjpp1/Baby/D92807365-_1_Tallhero_2xx._CB598669664_.jpg" class="d-block w-100" alt="..." style={{height:"300px"}}/>
    </div>
    <div class="carousel-item" data-bs-interval="5000">
      <img src="https://images-eu.ssl-images-amazon.com/images/G/31/img15/4th/sept/unrechero/Tws_Tallhero_3000x1200._CB596104802_.jpg" class="d-block w-100" alt="..." style={{height:"300px"}}/>
    </div>
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
</div>
    </div>
  )
}
