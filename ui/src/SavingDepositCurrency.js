
import React, { useEffect, useState } from 'react'

const SavingDepositCurrency = (props) => {
    const [deposit, setDeposit] = useState([])
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        setLoading(true);
        fetch(`/api/deposits/${props.id}`, { method: 'GET' })
            .then(response => response.json())
            .then(result => {
                setDeposit(result);
                setLoading(false);
            })
    }, []);

    if (loading) {
        return (
        <td>Loading...</td>
        )
    }
    return (<td>{deposit.currency}</td>
    )
}


export default SavingDepositCurrency