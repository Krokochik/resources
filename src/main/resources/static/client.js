var requestUrl = 'https://random--number.herokuapp.com/post/3~01YZH>Iysc8~e><A8x'

function sendRequest(method, url, body = null) {
  const headers = {
    'Content-Type': 'application/json'
  }

  return fetch(url, {
    method: method,
    body: JSON.stringify(body),
    headers: headers
  }).then(response => {
    if (response.ok) {
      return response.json()
    }

    return response.json().then(error => {
      const e = new Error('Что-то пошло не так')
      e.data = error
      throw e
    })
  })
}

/*
sendRequest('GET', requestUrl)
  .then(data => console.log(data))
  .catch(err => console.log(err))
*/
///*
const body = {
  id: 2,
}

sendRequest('POST', requestUrl, body)
  .then(data => console.log(data))
  .catch(err => console.log(err))
//*/
