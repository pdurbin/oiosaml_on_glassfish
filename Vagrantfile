# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant::Config.run do |config|

  config.vm.box = "puppet-vagrant-boxes-centos-64-x64-vbox4210"
  config.vm.box_url = "http://puppet-vagrant-boxes.puppetlabs.com/centos-64-x64-vbox4210.box"
  config.vm.share_folder "downloads", "/downloads", "downloads"
  config.vm.share_folder "oiosaml_on_glassfish", "/home/vagrant/oiosaml_on_glassfish", "oiosaml_on_glassfish"
  config.vm.forward_port 8080, 9999

  config.vm.provision :puppet do |puppet|
    puppet.manifests_path = "manifests"
    puppet.manifest_file  = "init.pp"
    puppet.module_path    = "modules"
  end

end
