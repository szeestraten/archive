// List of tasks
var tasks = [];

// Other elements
var myTasks = document.getElementById("myTasks");
var myButton = document.getElementById("myButton");
var myOutput = document.getElementById("myOutput");
var checkboxes = document.getElementsByName("checkbox");
var taskListItems = document.getElementsByTagName("li");

// Helper function to create list item with a task
function createTask(input, timestamp) {
  // Create new list item
  var li = document.createElement("li");

  // Create new label
  var label = document.createElement("label");
      label.id = timestamp;

  // Create new checkbox
  var checkbox = document.createElement("input");
      checkbox.type = "checkbox"
      checkbox.name = "checkbox"

  // Add checkbox to label
  label.appendChild(checkbox);

  // Add text to label
  label.appendChild(document.createTextNode(input + ': ' + timestamp));

  // Add label to list item
  li.appendChild(label);

  return li;
}


// Helper function to update the myOutput field
function updateCompletedTasks() {
  // Count the completed
  var completed = 0;
  for (var i = 0; i < checkboxes.length; i++) {
    if (checkboxes[i].checked){
      completed++;
    }
  }

  // Set myOutput
  myOutput.value = completed + "/" + checkboxes.length + " completed";
}


// Add new task to list
myButton.addEventListener("click", function(){
  // Get input
  var input = document.getElementById("myInput").value;
  // Get timestamp
  var timestamp = Date.now();

  // Add input and timestamp to JS task list
  tasks.unshift({task: input, timestamp: timestamp});

  // Insert new task to the beginning of the unordered list
  myTasks.insertBefore(createTask(input, timestamp), myTasks.firstChild);

  // Go through all checkboxes
  updateCompletedTasks();
});


// Change tasks in list when clicked
myTasks.addEventListener("change", function(e){
  // Get the task label
  var label = e.target.parentNode;
  // Get the task list item
  var item = e.target.parentNode.parentNode;

  // If the checkbox is checked
  if (e.target.checked){
    // Strike line through label
    label.style.textDecoration = "line-through";
    // Remove task list item
    var node = item.parentNode.removeChild(item);
    // Append task to the end
    myTasks.appendChild(node);
  }

  // If the checkbox is unchecked
  else {
    // Remove line through from label
    label.style.textDecoration = "none";
    // Remove task list item
    var node = item.parentNode.removeChild(item);
    // Lookup position in array
    var index = tasks.findIndex(item => item.timestamp === parseInt(node.firstChild.id));
    // TODO: Add sorting 
  }

  // Go through all checkboxes
  updateCompletedTasks();
});
