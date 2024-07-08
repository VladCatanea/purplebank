"use client"

import React, { useState } from "react"
import { Button, Container, Form, Input, Label } from 'reactstrap'
import { useCookies } from "react-cookie"


const Login = () => {
    const [formState, setFormState] = useState({ username: "", password: "" })
    const [cookie, setCookie] = useCookies(['loggedIn']);
    const [message, setMessage] = useState("")
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
        await fetch('/login', {
            method: 'POST',
            body: formData,
        }).catch(error => {
                console.log(error)   
            })
        const response = await fetch('/api/permission', {
            method: 'GET'
        }).catch(error => console.log(error))
        if (response?.status == 302){
            setCookie("loggedIn", true)
        }
        else{
            console.log(response)
            setMessage("Invalid Credentials")
        }
    }



    return (<div>
        <Container>
            <Form>
                <br /><br />
                <Label>Username</Label>
                <br />
                <Input className="w-25"
                    type="text"
                    name="username"
                    value={formState.username}
                    onChange={handleInputChange} />
                <br />
                <Label>Password</Label>
                <br />
                <Input className="w-25"
                    type="password"
                    name="password"
                    value={formState.password}
                    onChange={handleInputChange} />
                <br /><br />
                <Button onClick={submitForm}>Submit</Button>
                <br /><br />
                {message}
            </Form>
        </Container>
    </div>
    )

}


export default Login