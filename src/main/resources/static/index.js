const addNewUserForm = document.querySelector("#add_new_user")
addNewUserForm.addEventListener('submit', (e) => {
    e.preventDefault();

    fetch(addNewUserForm.action, {
        method: 'POST',
        /*headers: {
             'Content-Type': 'application/json;charset=utf-8'
         },*/
        body: new FormData(addNewUserForm),
        redirect:"manual",
        referrerPolicy:"origin"
    }).then((data) => {
        console.log(data)
    })
        .catch()
})
const modalDelete = document.querySelectorAll("#modal_delete"),
    modalUpdate = document.querySelectorAll("#modal_update");
let delForm;
modalDelete.forEach((e) => {
    delForm = e.querySelector('[id^="delete_form"]')
    let userId = e.querySelector('[name="id"]')

    delForm.addEventListener('submit', (e) => {
        e.preventDefault()
        const requestBody = {id: userId.value}
        console.log(requestBody)
        console.log(delForm.action)
        const requestOptions = {
            method: 'DELETE',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(requestBody),

        };
        console.log(requestOptions)
        fetch(delForm.action+"/"+userId.value, requestOptions)
            .then((re) => re.json())
            .then(data => console.log(data))
            .catch(reason => console.log(reason))
    })
})
modalUpdate.forEach((e) => {
    updateForm = e.querySelector('[id^="update_form"]')
    let userId = e.querySelector('[name="id"]'),
        userName=e.querySelector('[name="name"]'),
        lastName=e.querySelector('[name="lastName"]'),
        date = e.querySelector('[name="date"]'),
        role = e.querySelector('[name="authorisation"]')


    updateForm.addEventListener('submit', (e) => {

        e.preventDefault()
        const requestBody = {
            name: userName.value,
            lastName: lastName.value,
            date: date.value,
            authorisation: role.value
        }

        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(requestBody)
        };
        console.log(requestOptions)
        fetch(updateForm.action+"/"+userId.value, requestOptions)
            .then((re) => re.json())
            .then(data => console.log(data))
            .catch(reason => console.log(reason))
    })
})
