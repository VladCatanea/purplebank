"use client"

import React, { useState } from 'react'
import AppNavbar from './AppNavbar'
import "./App.css"

const Home = () => {
	const initialDeposits = []

	const [deposits, setDeposits] = useState([])
	const [loading, setLoading] = useState(false);

	return (
		<div>
      <AppNavbar/>
			<div className="container">
				<h1>CRUD App with Hooks</h1>
				<a href="/deposits">View all deposits</a>
			</div>
		</div>
	)
}

export default Home
