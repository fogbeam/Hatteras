Hatteras
========

Hatteras is a Business Event Subscriptions Engine from [Fogbeam Labs](http://www.fogbeam.com). 
Part of the [Fogcutter project](http://code.google.com/p/fogcutter), Hatteras provides a 
bridge between your Enterprises ESB/SOA infrastructure and  user-facing applications, by allowing users to define 
subscriptions to business events they are interested in. Hatteras delivers matching messages to the user application 
for display, additional processing, or other interaction.  Hatteras can also route events to other consumers including
Enterprise Social Networks, Semantic Processing Pipelines, Analytics Engines, etc.


Hatteras currently interoperates with [Quoddy](http://code.google.com/p/quoddy) - the Enterprise Social Network component of Fogcutter - to allow users 
to have Business Events rendered into their Event Stream alongside Status Updates, Calendar Events, and other 
ActivityStream items.

Hatteras is written in Java and makes heavy use of Apache Camel for message routing. In it's current form, Hatteras 
uses XQuery expressions for matching XML messages "off the wire" and includes support for the OAGIS vocabulary. 
Subsequent work will add support for xCBL and OASIS UBL as well as customer-defined schemas and vocabularies.



Commercial Support
------------------

Commercial support is available from [Fogbeam Labs](http://www.fogbeam.com).  
