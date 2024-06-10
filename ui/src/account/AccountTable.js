import React from 'react'
import { Table } from 'reactstrap'

const AccountsTable = (props) => {
	return(
	<Table className="mt-4">
		<thead>
			<tr>
				<th width="15%">IBAN</th>
				<th width="10%">Currency</th>
				<th width="10%">Amount</th>
				<th width="10%">Full name</th>
				<th width="10%">TIN</th>
				<th width="15%">Address</th>
				<th width="10%">Phone Number</th>
				<th width="10%">Creation Date</th>
			</tr>
		</thead>
		<tbody>
			{props.accounts.map((account) => (
				<tr key={account.iban}>
					<td>{account.iban}</td>
					<td>{account.currency}</td>
					<td>{account.amount}</td>
					<td>{account.ownerFullName}</td>
					<td>{account.tin}</td>
					<td>{account.address}</td>
					<td>{account.phone}</td>
					<td>{account.creationDate.split('T')[0]}</td>
				</tr>
			))}
		</tbody>
	</Table>
)
}
export default AccountsTable