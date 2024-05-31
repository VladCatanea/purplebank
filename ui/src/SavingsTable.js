import React, { useEffect, useState } from 'react'
import { Table } from 'reactstrap'
import SavingDepositCurrency from './SavingDepositCurrency'

const SavingsTable = (props) => {

	const initialDeposit = {id: 0, currency:"", duration:0};
	const [deposits, setDeposits] = useState(initialDeposit)
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        setLoading(true);
        fetch(`/api/deposits`, { method: 'GET' })
            .then(response => response.json())
            .then(result => {
                setDeposits(result);
                setLoading(false);
            })
    }, []);

	return (
		<Table className="mt-4">
			<thead>
				<tr>
					<th width="10%">Id</th>
					<th width="15%">Currency</th>
					<th width="15%">Amount</th>
					<th width="20%">Expiration Date</th>
					<th width="20%">Expiration Time</th>
					<th width="20%"><div align="left">Actions</div></th>
				</tr>
			</thead>
			<tbody>
				{props.savings.map((saving) => (
					<tr key={saving.id}>
						<td>{saving.id}</td>
						<SavingDepositCurrency id={saving.depositId} />
						<td>{saving.amount}</td>
						<td>{saving.expiration.split("T")[0]}</td>
						<td>{saving.expiration.split("T")[1].split(".")[0]}</td>
						<td>
							<button className="button delete" size="sm" onClick={() => props.remove(saving.id)}>Delete Savings</button>
						</td>
					</tr>
				))}
			</tbody>
		</Table>
)
}

export default SavingsTable