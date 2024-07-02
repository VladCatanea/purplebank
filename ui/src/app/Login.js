"use client"

import React, { useState } from "react"
import { Button, Container, Form, Input, Label } from 'reactstrap'
import { useCookies } from "react-cookie"


const Login = () => {
    const [formState, setFormState] = useState({ username: "", password: "" })
    const [cookies, setCookie, removeCookie] = useCookies(['loggedIn']);
    setCookie("loggedIn", false)

    const handleInputChange = (event) => {
        const { name, value } = event.target

        setFormState({ ...formState, [name]: value })
    }

    const submitForm = async (event) => {
        event.preventDefault()
        const formData = new FormData()
        formData.append("username", formState.username)
        formData.append("password", formState.password)
        console.log(formData)
        await fetch('/login', {
            method: 'POST',
            body: formData,
        })
            .then(response => response.json())
            .then(result => {
                // Do something with the successful response
                console.log(result)
                setCookie("loggedIn", true)
            })
            .catch(error => console.log(error)
            )
            setCookie("loggedIn", true)
    }



    return (<div>
        <Container>
        <Form>
            <Label>Username</Label>
            <br />
            <Input className="w-25"
                type="text"
                name="username"
                value={formState.username}
                onChange={handleInputChange} />
            <br /><br />
            <Label>Password</Label>
            <br />
            <Input className="w-25"
                type="password"
                name="password"
                value={formState.password}
                onChange={handleInputChange} />
            <br /><br />
            <Button onClick={submitForm}>Submit</Button>
        </Form>
        </Container>
    </div>
    )

}


export default Login