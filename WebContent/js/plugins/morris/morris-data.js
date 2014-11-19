$(function() {

    Morris.Area({
        element: 'morris-area-chart',
        data: [{
            period: '2012 Q3',
            sales_product: 2666,
            sales_product2: null,
            sales_product3: 2647
        }, {
            period: '2012 Q4',
            sales_product: 2778,
            sales_product2: 2294,
            sales_product3: 2441
        }, {
            period: '2013 Q1',
            sales_product: 4912,
            sales_product2: 1969,
            sales_product3: 2501
        }, {
            period: '2013 Q2',
            sales_product: 3767,
            sales_product2: 3597,
            sales_product3: 5689
        }, {
            period: '2013 Q3',
            sales_product: 6810,
            sales_product2: 1914,
            sales_product3: 2293
        }, {
            period: '2013 Q4',
            sales_product: 5670,
            sales_product2: 4293,
            sales_product3: 1881
        }, {
            period: '2014 Q1',
            sales_product: 4820,
            sales_product2: 3795,
            sales_product3: 1588
        }, {
            period: '2014 Q2',
            sales_product: 15073,
            sales_product2: 5967,
            sales_product3: 5175
        }, {
            period: '2014 Q3',
            sales_product: 10687,
            sales_product2: 4460,
            sales_product3: 2028
        }, {
            period: '2014 Q4',
            sales_product: 8432,
            sales_product2: 5713,
            sales_product3: 1791
        }],
        xkey: 'period',
        ykeys: ['sales_product', 'sales_product2', 'sales_product3'],
        labels: ['Sales Apples', 'Sales Potatoes', 'Sales Carrots'],
        pointSize: 2,
        hideHover: 'auto',
        resize: true
    });

    Morris.Donut({
        element: 'morris-donut-chart',
        data: [{
            label: "University of Mannheim",
            value: 210
        }, {
            label: "Mannheim Mainstation",
            value: 122
        }, {
            label: "SAP SE",
            value: 200
        }],
        resize: true
    });


});
