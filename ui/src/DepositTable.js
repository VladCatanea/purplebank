import React from 'react'

const DepositTable = (props) => (
	<table>
		<thead>
			<tr>
				<th>Id</th>
				<th>Currency</th>
				<th>Duration</th>
			</tr>
		</thead>
		<tbody>
			{props.deposits.length > 0 ? (props.deposits.map((deposit) => (
				<tr>
					<td>{deposit.id}</td>
					<td>{deposit.currency}</td>
					<td>{deposit.duration}</td>
				</tr>
			))) : (<tr>
				<td colSpan={3}>No deposits</td>
			</tr>)}
		</tbody>
	</table>
)

export default DepositTable