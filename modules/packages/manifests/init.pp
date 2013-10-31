class packages {

  $my_packages = [
    'java-1.6.0-openjdk',
    'apache-maven',
  ]

  package { $my_packages:
    ensure => installed,
  }

}
