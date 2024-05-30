"use client"

import React, { useEffect, useState } from 'react'
import SavingsTable from './SavingsTable'
import AppNavbar from './AppNavbar'
import "./App.css"
import { Container } from 'reactstrap'

const SavingsList = () => {
    const [savings, setSavings] = useState([])
    const [loading, setLoading] = useState(false);
    // const [permission, setPermission] = useState("");

    useEffect(() => {
        setLoading(true);
        fetch('/api/savings', { method: 'GET' })
            .then(response => response.json())
            .then(result => {
                setSavings(result);
                setLoading(false);
            })
    }, []);

    // useEffect(() => {
    //     setLoading(true);
    //     fetch('/api/permission', { method: 'GET' })
    //         .then(response => response.json())
    //         .then(result => {
    //             setPermission(result.permission);
    //             setLoading(false);
    //         })
    // }, []);

    const remove = async (id) => {
        await fetch(`/api/savings/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            fetch('/api/savingss', { method: 'GET' })
                .then(response => response.json())
                .then(result => {
                    setSavings(result);
                })
        });
    }

    if (loading) {
        return <p>Loading...</p>
    }


    return (
        <div>
            <AppNavbar />
            <Container fluid>
                <div>
                    {/* <div className="float-end">
                        <button className="button create" onClick={() => window.location.href = "/savings/new"}>Add savings</button>
                    </div> */}
                    <h1>Savings List</h1>
                    <SavingsTable savings={savings} remove={remove} />
                </div>
            </Container>
        </div>
    )
}

export default SavingsList
