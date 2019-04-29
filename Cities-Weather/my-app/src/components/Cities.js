import React, {Component} from 'react';
import {Link } from "react-router-dom";
class Cities extends Component{

  constructor(props){
    super(props);
    this.state = {
        items: [],
        isLoaded: false,
        cityName: '',
        cityCountry: '',
    }


    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleNameChange = this.handleNameChange.bind(this);
    this.handleCountryChange = this.handleCountryChange.bind(this);
}

      handleNameChange (evt) {
        this.setState({ cityName: evt.target.value});
      }

      handleCountryChange (evt) {
        this.setState({ cityCountry: evt.target.value});
      }




  handleSubmit(event) {
    fetch("http://localhost:8080/api/cities", {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: this.state.cityName,
          country: this.state.cityCountry,
        })
      })
  }


  componentDidMount(){
    fetch("http://localhost:8080/api/cities")
    .then(res => res.json())
    .then(json => {
      this.setState({
        isLoaded:true,
        items:json,
      })
    }).catch(
    )
  }

render(){
  var { isLoaded,items} = this.state;

  if(!isLoaded){
    return <div>Loading...</div>
  }else{
    return (
        <div className="App">
            <div className="row">
              <section className="col-md-6 ">
                <h1>Cidades</h1>
                <ul>
                    {items.map(item =>(
                      <li key={item.id}>
                        <b>Name: </b><Link to={"/city/"+item.country+"/"+item.name}>{item.name}</Link><b> Country: </b> {item.country}
                      </li>
                    ))}
                </ul>
              </section>
              <section className="col-md-6">
                <h1>Cadastro de Cidades</h1>
                <form onSubmit={this.handleSubmit}>
                  <label>
                    Name:
                    <input type="text" value={this.state.cityName} onChange={this.handleNameChange}  />
                  </label>
                  <label>
                    Country:
                    <input type="text" value={this.state.cityCountry} onChange={this.handleCountryChange}  />
                  </label>
                  <input type="submit" value="Submit" />
                </form>
              </section>
            </div>
        </div>
      );
  }
}
}
export default Cities;
