import axios from 'axios'

const http = axios.create({
    baseURL: "https://swat-services.onrender.com/api",
    Accept: "*/*",
    headers: {
        "Content-type": "application/json"
    }
});

export default http;