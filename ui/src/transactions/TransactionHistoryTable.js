import React from 'react'
import { Table } from 'reactstrap'

const TransactionHistoryTable = (props) => {
	return(
	<Table className="mt-4">
		<thead>
			<tr>
				<th width="10%">Reference number</th>
				<th width="15%">Receiver IBAN</th>
				<th width="15%"> Sender IBAN</th>
				<th width="15%">Sender Name</th>
                <th width="10%">Transaction Date</th>
                <th width="10%">Authorization Code</th>
                <th width="10%">Debit Amount</th>
                <th width="10%">Credit Amount</th>
			</tr>
		</thead>
		<tbody>
			{props.transactions.map((transaction) => (
				<tr key={transaction.referenceNum}>
					<td>{transaction.referenceNum}</td>
					<td>{transaction.receiverIban}</td>
					<td>{transaction.senderIban}</td>
					<td>{transaction.senderName}</td>
                    <td>{transaction.transactionDate}</td>
                    <td>{transaction.authorizationCode}</td>
                    <td>{transaction.debitAmount}</td>
                    <td>{transaction.creditAmount}</td>
				</tr>
			))}
		</tbody>
	</Table>
)
}
export default TransactionHistoryTable