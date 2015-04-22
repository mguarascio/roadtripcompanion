# Introduction #

Describes how to access the roadtripcompanion subversion (svn) source code repository using the Subclipse Eclipse plugin.  These instructions are tuned to Eclipse version 3.4, Ganymede.

Plugin information: http://subclipse.tigris.org/

# Steps to Install #
  1. Open Eclipse and select Help > Software Updates... from the toolbar
  1. Click on the Available Software tab, then Add Site...
  1. In the Location... dialog, enter the Subclipse update site and click OK:  http://subclipse.tigris.org/update_1.6.x
  1. Check the checkbox next to the newly added subclipse URL, and all the child checkboxes
  1. Click the Install... button on the right
  1. Once the plugin is installed you will be prompted to restart Eclipse...click Yes
  1. In Eclipse, open the SVN Repositories View
  1. Right click and select New > Repository Location...
  1. In the URL textbox, enter the svn repository location of roadtripcompanion:  https://roadtripcompanion.googlecode.com/svn/trunk
  1. Once the repository location is setup, expand the arrow next to the repository and  right-click the roadtripcompanion project
  1. Select Check Out... and the project should now be in your local workspace