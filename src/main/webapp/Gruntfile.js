module.exports = function(grunt) {
	grunt.initConfig({
		jshint : [ 'js/account/*.js' ],
		concat : {
			js : {
				files : {
					'build/js/bundle.js': 'js/account/*.js'
				}
			}
		},
		uglify : {
			bundle : {
				files : {
					'build/js/bundle.min.js': 'build/js/bundle.js'
				}
			}
		},
		clean : {
			js : 'build/js/bundle.js'
		}
	});
	grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-contrib-clean');

	grunt.registerTask('default', [ 'jshint', 'concat:js', 'uglify:bundle', 'clean:js' ]);
};