import React, {Component} from "react";
import Moment from 'react-moment';

class City extends Component{
  constructor(props){
      super(props);
      this.state = {
          items: [],
          isLoaded: false,
          cityName: this.props.match.params.name,
          cityCountry:this.props.match.params.country,
      }
  }




  componentDidMount(){
    fetch("http://localhost:8080/api/forecast/"+this.state.cityCountry+"/"+this.state.cityName)
    .then(res => res.json())
    .then(json => {
      this.setState({
        isLoaded:true,
        items: json.list,
      })
    })
  }

  render(){
    var { isLoaded,items} = this.state;

    if(!isLoaded){
      return <div>Loading...</div>
    }else{
      return (
          <div className="App">
              <div className="row">
                <section className="col-md-12 ">
                  <h1>Previsão</h1>
                  <h2>Cidade: {this.state.cityName}</h2>
                  <ul>
                  {items.map(item =>(
                    <li>
                      <b>Dia:</b><Moment unix format="DD/MM/YYYY">{item.dt}</Moment> <b>Temp. Mínima:</b> {item.temp.min} <b>Temp. Máxima:</b> {item.temp.max} <b>Condição:</b> {item.weather[0].description}
                    </li>
                  ))}
                  </ul>
                </section>
              </div>
          </div>
        );
    }
  }

}

export default City;
