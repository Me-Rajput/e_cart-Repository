import React, { useEffect } from 'react'
import { useState } from 'react'
import { getAllBrandName, getAllPriceRange } from '../service/FilterConnection'

export default function Filter(props) {
    const id = props.productId
    const [brandNameData, setBrandNameData] = useState([])
    const [priceRangeData, setPriceRangeData] = useState([])
    const [filterBrand, setFilterBrand] = useState([])
    const [filterStarData, setFilterStarData] = useState([])
    const [filterPriceData, setFilterPriceData] = useState([])

    useEffect(() => {
        props.sendStarFilterData(filterStarData)
    }, [filterStarData])
    useEffect(() => {
        props.sendBrandFilterID(filterBrand)
    }, [filterBrand])
    useEffect(() => {
        props.sendPriceFilterData(filterPriceData)
    }, [filterPriceData])
    useEffect(() => {
        var brandCheckBox = document.getElementsByClassName('brandCheckbox');
        for (var x = 0; x < brandCheckBox.length; ++x) {
            brandCheckBox[x].checked = false
        }
        var starCheckBox = document.getElementsByClassName('starCheckBox');
        for (var x = 0; x < starCheckBox.length; x++) {
            starCheckBox[x].checked = false
        }
        var priceRadioButton = document.getElementsByClassName('priceRadioBtn');
        for (var x = 0; x < priceRadioButton.length; x++) {
            priceRadioButton[x].checked = false
        }
        getAllBrandName(id).then((response) => {
            setBrandNameData(response)
            setFilterBrand([])
            setFilterStarData([])
        }).catch((error) => {
            console.log(error)
        })
        getAllPriceRange(id).then((response) => {
            console.log(response)
            setPriceRangeData(response)
        }).catch((error) => {
            console.log(error)
        })
    }, [id])

    const removeDataFromFilter = (event) => {
        setFilterBrand([...filterBrand.filter((data) =>
            data !== event.target.value
        )])
    }
    const handelFilterValue = (event) => {
        if (event.target.checked) {
            setFilterBrand([...filterBrand, event.target.value])
        } else {
            removeDataFromFilter(event)
        }
    }
    const handelFilterStarValue = (event) => {
        setFilterStarData([event.target.value])
    }
    const handelFilterPriceValue = (event) => {
        setFilterPriceData([event.target.value])
    }
    return (
        <div>
            <div class="accordion" id="accordionPanelsStayOpenExample">
                <div class="accordion-item">
                    <h2 class="accordion-header" >
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="true" aria-controls="panelsStayOpen-collapseOne">
                            Price Filter
                        </button>
                    </h2>
                    <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse show" >
                        <div class="accordion-body" >
                            <input class="form-check-input" type="radio" name="flexRadioDefault" className='priceRadioBtn' value={"<" + priceRangeData.priceRange1} id="flexRadioDefault1" onClick={(e) => handelFilterPriceValue(e)} />
                            <label class="form-check-label" for="flexRadioDefault1">
                                Below  {priceRangeData.priceRange1}
                            </label>
                            <br />

                            <input class="form-check-input" type="radio" name="flexRadioDefault" className='priceRadioBtn' value={"<" + priceRangeData.priceRange2} id="flexRadioDefault2" onClick={(e) => handelFilterPriceValue(e)} />
                            <label class="form-check-label" for="flexRadioDefault2">
                                Below  {priceRangeData.priceRange2}
                            </label>
                            <br />

                            <input class="form-check-input" type="radio" name="flexRadioDefault" className='priceRadioBtn' value={"<" + priceRangeData.priceRange3} id="flexRadioDefault2" onClick={(e) => handelFilterPriceValue(e)} />
                            <label class="form-check-label" for="flexRadioDefault2">
                                Below  {priceRangeData.priceRange3}
                            </label>
                            <br />

                            <input class="form-check-input" type="radio" name="flexRadioDefault" className='priceRadioBtn' value={"<" + priceRangeData.priceRange4} id="flexRadioDefault2" onClick={(e) => handelFilterPriceValue(e)} />
                            <label class="form-check-label" for="flexRadioDefault2">
                                Below  {priceRangeData.priceRange4}
                            </label>
                            <br />

                            <input class="form-check-input" type="radio" name="flexRadioDefault" className='priceRadioBtn' value={"<" + priceRangeData.priceRange5} id="flexRadioDefault2" onClick={(e) => handelFilterPriceValue(e)} />
                            <label class="form-check-label" for="flexRadioDefault2">
                                Below  {priceRangeData.priceRange5}
                            </label>
                            <br />

                            <input class="form-check-input" type="radio" name="flexRadioDefault" className='priceRadioBtn' value={">" + priceRangeData.priceRange5} id="flexRadioDefault2" onClick={(e) => handelFilterPriceValue(e)} />
                            <label class="form-check-label" for="flexRadioDefault2">
                                {priceRangeData.priceRange5}+
                            </label>

                        </div>
                    </div>
                </div>
                <div class="accordion-item">
                    <h2 class="accordion-header">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseTwo" aria-expanded="false" aria-controls="panelsStayOpen-collapseTwo">
                            Brand Filter
                        </button>
                    </h2>
                    <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse">
                        <div class="accordion-body">
                            {
                                brandNameData.map((productData) => (
                                    <>
                                        <input type="checkbox" id={productData.subsub_id} className='brandCheckbox' name={productData.brande_name} value={productData.subsub_id} onClick={(e) => handelFilterValue(e)} />
                                        <label for="vehicle1"> {productData.brande_name}</label> <br />
                                    </>
                                ))
                            }
                        </div>
                    </div>
                </div>
                <div class="accordion-item">
                    <h2 class="accordion-header">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseThree" aria-expanded="false" aria-controls="panelsStayOpen-collapseThree">
                            Customer Rating
                        </button>
                    </h2>
                    <div id="panelsStayOpen-collapseThree" class="accordion-collapse collapse">
                        <div class="accordion-body">
                            <input type="radio" name="starFilterRadio" value="4" className='starCheckBox' onClick={(e) => handelFilterStarValue(e)} />
                            <label > 4 <span className="fa fa-star checked" /> & above</label><br />

                            <input type="radio" name="starFilterRadio" value="3" className='starCheckBox' onClick={(e) => handelFilterStarValue(e)} />
                            <label > 3 <span className="fa fa-star checked" /> & above</label><br />

                            <input type="radio" name="starFilterRadio" value="2" className='starCheckBox' onClick={(e) => handelFilterStarValue(e)} />
                            <label > 2 <span className="fa fa-star checked" /> & above</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
