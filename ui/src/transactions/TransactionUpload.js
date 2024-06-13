import React, { useEffect, useState } from 'react'
import { Button, Container, Form, Input, Label } from 'reactstrap';
import AppNavBar from '../app/AppNavbar'
import { ROLE_ADMIN } from '../app/Constants';

const TransactionUpload = () => {
  const [file, setFile] = useState();
  const [isSelected, setIsSelected] = useState(false);
  const [successMessage, setSuccessMessage] = useState("")


  const [permission, setPermission] = useState()

	useEffect( () => {
		fetch("/api/permission", {method: "GET"})
		.then(response => response.json())
		.then(result => {
			setPermission(result.permission)
		})
	}, [])

  if (permission !== ROLE_ADMIN){
    return (<div>
      <AppNavBar/>
      <div>Only admin user allowed on this page</div>
    </div>)
  }

  const handleInputChange = (event) => {

    setFile(event.target.files[0])
    setIsSelected(true)
  }

  const handleUpload = (event) => {
    event.preventDefault()
    uploadFile(file)
  }

  function uploadFile(file) {
    const formData = new FormData();
    formData.append("file", file);
    fetch('/api/transactions', {
      // content-type header should not be specified!
      method: 'POST',
      body: formData,
    })
      .then(response => response.json())
      .then(result => {
        // Do something with the successful response
        setSuccessMessage("Transactions proccessed: " + result.toString())
      })
      .catch(error => console.log(error)
      );
  }

  return (
    <div>
      <AppNavBar />
      <Container>
        <Form>
          {isSelected ? (
            <div>
              <p>Filename: {file.name}</p>
              <p>Filetype: {file.type}</p>
              <p>Size in bytes: {file.size}</p>
            </div>
          ) : (
            <p>Select a file to show details</p>
          )}
          <Label>File listing transactions (JSON or CSV format)</Label>
          <br />
          <Input type="file" name="filename" onChange={handleInputChange} />
          <br /><br />
          <Button color="primary" onClick={handleUpload}>Upload</Button>

        </Form>
        <br />
        <div>{successMessage}</div>
      </Container>
    </div>
  );

}

export default TransactionUpload