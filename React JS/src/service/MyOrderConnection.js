import axios from "axios";

export const Base_URL="http://localhost:8080"

export const cancelOrderedItem=(orderId, customerID)=>{
    return axios.get(`${Base_URL}/order/cancelorder/${orderId}/${customerID}`).then((response)=> response.data)
}
export const downloadInvoice=(orderId)=>{
        fetch(`${Base_URL}/order/downloadinvoice/${orderId}`).then((response)=> response.blob()).then((blob)=>{
        const blobURL=window.URL.createObjectURL(new Blob([blob]))
        const fileName="Invoice.pdf";
        const aTag=document.createElement("a");
        aTag.href=blobURL;
        aTag.setAttribute("download",fileName)
        document.body.appendChild(aTag)
        aTag.click()
        aTag.remove()
    })
}