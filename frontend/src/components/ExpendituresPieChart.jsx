import React, { useEffect, useState } from 'react';
import { Pie } from 'react-chartjs-2';
import axios from 'axios';
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
} from 'chart.js';

ChartJS.register(ArcElement, Tooltip, Legend);

const ExpendituresPieChart = () => {
  const [chartData, setChartData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [selectedOrgan, setSelectedOrgan] = useState('all');
  const [selectedBranch, setSelectedBranch] = useState('EXECUTIVE');
  const [allExpenditures, setAllExpenditures] = useState([]);
  const localeStyle = { style: 'currency', currency: 'BRL' };

  const year = 2025;
  const page = 1;

  useEffect(() => {
    axios.get(`/api/expenditures/${year}?page=${page}`)
      .then(response => {
        setAllExpenditures(response.data);
        setLoading(false);
        setChartData(generateChartData(response.data));
      })
      .catch(error => {
        console.error('Error fetching organ list:', error);
      });
  }, []);

  const handleOrganChange = (event) => {
    const organCode = event.target.value;
    setSelectedOrgan(organCode);

    if (organCode === 'all') {
      setChartData(generateChartData(allExpenditures));
    } else {
      const organExpenditures = allExpenditures.filter(item => item.organCode === organCode);
      setChartData(generateChartData(organExpenditures));
    }
  };

  const handleBranchChange = (e) => {
    setSelectedBranch(e.target.value);
  };

  const generateChartData = (expenditures) => {
    const sortedExpenditures = expenditures.sort((a, b) => b.paid - a.paid);
    const label = sortedExpenditures.map(item =>
      `${item.organName} - (${item.paid.toLocaleString('pt-BR', localeStyle)})`
    );
    const paid = sortedExpenditures.map(item => item.paid);
    const backgroundColors = [
      generateRandomColor(), generateRandomColor(), generateRandomColor(), generateRandomColor(),
      generateRandomColor(), generateRandomColor(), generateRandomColor(), generateRandomColor(),
      generateRandomColor(), generateRandomColor(), generateRandomColor(), generateRandomColor(),
    ];

    return {
      labels: label,
      datasets: [
        {
          label: 'Total paid (R$)',
          data: paid,
          backgroundColor: backgroundColors,
          borderWidth: 1,
        }
      ]
    };
  };

  const getBranchData = (branch) => {
    const sortedExpenditures = allExpenditures.sort((a, b) => b.paid - a.paid);
    const branchExpenditures = sortedExpenditures.filter(item => item.branch === branch);
    const label = branchExpenditures.map(item =>
      `${item.organName} - (${item.paid.toLocaleString('pt-BR', localeStyle)})`
    );
    const paid = branchExpenditures.map(item => item.paid);
    const backgroundColors = [
      generateRandomColor(), generateRandomColor(), generateRandomColor(), generateRandomColor(),
      generateRandomColor(), generateRandomColor(), generateRandomColor(), generateRandomColor(),
      generateRandomColor(), generateRandomColor(), generateRandomColor(), generateRandomColor(),
    ];

    return {
      labels: label,
      datasets: [
        {
          label: `Total paid (R$)`,
          data: paid,
          backgroundColor: backgroundColors,
          borderWidth: 1,
        }
      ]
    };
  };

  const generateRandomColor = () => {
    const r = Math.floor(Math.random() * 256);
    const g = Math.floor(Math.random() * 256);
    const b = Math.floor(Math.random() * 256);
    return `rgb(${r},${g},${b})`;
  };

  if (loading) return <p>Loading chart...</p>;

  const allExpendituresChartOptions = {
    plugins: {
      legend: {
        position: 'left',
        maxWidth: 500,
        labels: {
          usePointStyle: true
        },
      },
    },
  };

  const expendituresByBranchChartOptions = {
    plugins: {
      legend: {
        position: 'right',
        maxWidth: 500,
        labels: {
          usePointStyle: true
        },
      },
    },
  };

  return (
    <div style={{ display: 'flex' }}>
      <div style={{ flex: 1 }}>
        <h3>Expenditures by Organ</h3>

        {/* Dropdown for selecting an organ */}
        <select onChange={handleOrganChange} value={selectedOrgan}>
          <option key="all" value="all">All Organs</option>
          {allExpenditures.map((expenditure) => (
            <option key={expenditure.organCode} value={expenditure.organCode}>
              {expenditure.organName}
            </option>
          ))}
        </select>

        {/* Pie chart for selected organ */}
        {selectedOrgan && chartData ? (
          <div>
            <Pie data={chartData} options={allExpendituresChartOptions} />
          </div>
        ) : null}
      </div>

      {/* Pie charts for all branches */}
      <div style={{ flex: 1.7, paddingLeft: '50px' }}>
        <h3>Expenditures Grouped by Branch</h3>
        <select onChange={handleBranchChange} value={selectedBranch}>
          {['EXECUTIVE', 'LEGISLATIVE', 'JUDICIARY'].map((branch) => (
            <option key={branch} value={branch}>
              {branch}
            </option>
          ))}
        </select>

        {/* Pie chart for selected branch */}
        {selectedBranch ? (
          <div style={{ maxHeight: '910px'}}>
            <Pie data={getBranchData(selectedBranch)} options={expendituresByBranchChartOptions} />
          </div>
        ) : null}
      </div>

    </div>
  );
};

export default ExpendituresPieChart;
