"use client"

import React, { useState } from 'react'
import AppNavbar from './AppNavbar'
import "./App.css"

const Home = () => {
	return (
		<div>
      <AppNavbar/>
			<div className="container">
				<h1>Purple Bank</h1>
				<a href="/deposits">View all deposits</a>
			</div>
		</div>
	)
}

export default Home
