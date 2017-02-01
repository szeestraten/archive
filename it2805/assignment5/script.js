/* Part 2 */
console.log('PART 2')
/* Loop over i from 1 to 20 and print to console */
for (i = 1; i < 21; i++) {
  console.log(i)
}


/* Part 3 */
console.log('PART 3')
const numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]

/* Loop through the array of numbers */
for (i = 0; i < numbers.length; i++) {
  /* Use an empty variable for the result string */
  var result = '';

  /* Check if number is divisble by 3 */
  if (numbers[i] % 3 === 0) {
    result += 'eple';
  }

  /* Check if number is divisble by 5 */
  if (numbers[i] % 5 === 0) {
    result += 'kake';
  }

  /* Check if number was not divisible by 3 or 5 */
  if (!result) {
    result = numbers[i];
  }

  /* Print out the result */
  console.log(result);
}


/* Part 4 */
document.getElementById('title').innerHTML = 'Hello, JavaScript';


/* Part 5 */
function changeDisplay(){
  document.getElementById('magic').style.display = 'none';
}

function changeVisibility(){
  document.getElementById('magic').style.display = 'block';
  document.getElementById('magic').style.visibility = 'hidden';
}

function reset(){
  document.getElementById('magic').style.display = 'block';
  document.getElementById('magic').style.visibility = 'visible';
}

/* Part 6 */
const technologies = [
  'HTML5',
  'CSS3',
  'JavaScript',
  'Python',
  'Java',
  'AJAX',
  'JSON',
  'React',
  'Angular',
  'Bootstrap',
  'Node.js'
];

/* Get the unorder list by id */
var ul = document.getElementById('tech');

/* Loop through the array of strings */
for (i = 0; i < technologies.length; i++) {
  /* Create a new list item */
  var li = document.createElement('li');

  /* Set the text of the new list item from the array */
  li.innerHTML = technologies[i];

  /* Append the list item to the unordered list */
  ul.appendChild(li);
}
