"use client"

import React, { useEffect, useState } from 'react'
import AppNavbar from './AppNavbar'
import "./App.css"
import {ROLE_ADMIN} from './Constants'
import { Link } from 'react-router-dom'
import { Button } from 'reactstrap'

const Home = () => {

	const [permission, setPermission] = useState()

	useEffect( () => {
		fetch("/api/permission", {method: "GET"})
		.then(response => response.json())
		.then(result => {
			setPermission(result.permission)
		})
	})

	return (
		<div>
      <AppNavbar/>
			<div className="container">
				<h1>Purple Bank</h1>
				<br/>
				{permission === ROLE_ADMIN ? (<Button color="primary" tag={Link} to='/transactions'>Upload transactions file</Button>) : (<div>Hello !</div>)}
			</div>
		</div>
	)
}

export default Home
